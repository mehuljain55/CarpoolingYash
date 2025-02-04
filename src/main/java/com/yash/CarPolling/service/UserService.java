package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.PickUpPlaces;
import com.yash.CarPolling.entity.Routes;
import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.RouteStatus;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.PickUpPlacesRepo;
import com.yash.CarPolling.repository.RoutesRepo;
import com.yash.CarPolling.repository.UserRepo;
import com.yash.CarPolling.repository.VechileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VechileRepo vechileRepo;

    @Autowired
    private RoutesRepo routesRepo;

    @Autowired
    private PickUpPlacesRepo pickUpPlacesRepo;


    private static final String LICENCE_DIR = "src/main/resources/Licence/";


    public ApiResponseModel addUser(User user, MultipartFile file)
    {
        Optional<User> userOptional=userRepo.findById(user.getEmailId());
        if(userOptional.isPresent())
        {
            return new ApiResponseModel<>(StatusResponse.failed,null,"User Already Exists");
        }
        try {

            if(file!=null) {
                System.out.println("Image Upload");
                String path= saveLicenceImage(file, user.getEmailId());
               user.setLicencePath(path);
               user.setLicence(DocumentStatus.updated);
            }else {
                user.setLicence(DocumentStatus.not_updated);
            }
        ApiResponseModel apiResponseModel=userAuthorizationService.addUser(user);
            return apiResponseModel;

        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel<>(StatusResponse.failed,null,"Unable to add user");
        }
    }

    public ApiResponseModel addRoutes(User user, Routes routes,String vechileNo)
    {
        Optional<Vechile> vechileOptional=vechileRepo.findById(vechileNo);
        try {
            Vechile vechile=vechileOptional.get();
            List<PickUpPlaces> pickUpPlacesList=routes.getPickUpPlaces();
            routes.setPickUpPlaces(null);
            routes.setVechile(vechile);
            routes.setUser(user);
            Routes saveRoute=routesRepo.save(routes);
            for(PickUpPlaces place:pickUpPlacesList)
            {
                place.setRoutes(saveRoute);
                pickUpPlacesRepo.save(place);
            }

            System.out.println("Route saved");
            return  new ApiResponseModel<>(StatusResponse.success,null,"Routes added");

        }catch (Exception e)
        {
            e.printStackTrace();
            return  new ApiResponseModel<>(StatusResponse.failed,null,"Unable to add routes");
        }
    }

    public  ApiResponseModel findRoutes(String source,String destination)
    {
        List<Routes> routesList=routesRepo.findRouteBySourceDestination(source,destination);

        List<Routes> routes=new ArrayList<>();

        for(Routes route: routesList)
        {
          Vechile vechile=routesRepo.findVechileByRouteNo(route.getRouteId());
          route.setVechile(vechile);
          routes.add(route);
        }

        return new ApiResponseModel<>(StatusResponse.success,routes,"Routes Found");
    }



    private String saveLicenceImage(MultipartFile file, String emailId) throws IOException {
        File directory = new File(LICENCE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(LICENCE_DIR + emailId + ".jpg");
        Files.write(filePath, file.getBytes());

        return filePath.toAbsolutePath().toString();
    }

}
