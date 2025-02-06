package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.*;
import com.yash.CarPolling.entity.enums.*;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.*;
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

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private BookingService bookingService;



    private static final String LICENCE_DIR = "src/main/resources/Licence/";


    public ApiResponseModel addUser(User user,String officeId, MultipartFile file)
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
            ApiResponseModel apiResponseModel=userAuthorizationService.addUser(user,officeId);
            return apiResponseModel;

        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel<>(StatusResponse.failed,null,"Unable to add user");
        }
    }

    public ApiResponseModel addRoutes(User user, Routes routes,String vechileNo,String city)
    {

        Optional<Vechile> vechileOptional=vechileRepo.findById(vechileNo);
        List<User> userList=new ArrayList<>();
        userList.add(user);
        try {
            if(user.getBookingStatus().equals(BookingStatus.booked))
            {
                return new ApiResponseModel<>(StatusResponse.failed,null,"Cancel previous booking to add route");
            }

            Vechile vechile=vechileOptional.get();
            List<PickUpPlaces> pickUpPlacesList=routes.getPickUpPlaces();
            routes.setPickUpPlaces(null);
            String route_source=routes.getSource().toUpperCase();
            String destination=routes.getDestination().toUpperCase();
            routes.setSource(route_source);
            routes.setDestination(destination);
            routes.setCity(city);
            routes.setVechile(vechile);
            routes.setUser(user);
            Routes saveRoute=routesRepo.save(routes);


            for(PickUpPlaces place:pickUpPlacesList)
            {
                String source=place.getPlaces();
                place.setPlaces(source.toUpperCase());
                place.setRoutes(saveRoute);
                place.setCity(city);
                pickUpPlacesRepo.save(place);
            }

            Bookings bookingRequest=new Bookings();
            bookingRequest.setRoutes(saveRoute);;

            Bookings  bookings=bookingRepo.save(bookingRequest);
            user.setBookings(bookings);
            user.setBookingStatus(BookingStatus.booked);
            userRepo.save(user);


            System.out.println("Route saved");
            return  new ApiResponseModel<>(StatusResponse.success,null,"Routes added");

        }catch (Exception e)
        {
            e.printStackTrace();
            return  new ApiResponseModel<>(StatusResponse.failed,null,"Unable to add routes");
        }
    }

    public  ApiResponseModel findRoutes(String source,String destination,String city)
    {
        List<Routes> routesList=routesRepo.findRouteBySourceDestination(source,destination,city);

        List<Routes> routes=new ArrayList<>();

        for(Routes route: routesList)
        {
          Vechile vechile=routesRepo.findVechileByRouteNo(route.getRouteId());
          route.setVechile(vechile);
          routes.add(route);
        }
        return new ApiResponseModel<>(StatusResponse.success,routes,"Routes Found");
    }



    public ApiResponseModel<List<String>> cityList()
    {
        List<String> cityList=officeRepo.findDistinctCities();
        return new ApiResponseModel<>(StatusResponse.success,cityList,"City List");
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
