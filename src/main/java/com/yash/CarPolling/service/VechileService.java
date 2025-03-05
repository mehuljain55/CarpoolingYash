package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.enums.VechileStatus;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.PickUpPlacesRepo;
import com.yash.CarPolling.repository.RoutesRepo;
import com.yash.CarPolling.repository.VechileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VechileService {

    @Autowired
    private VechileRepo vechileRepo;

    @Autowired
    private RoutesRepo routesRepo;

    @Autowired
    private PickUpPlacesRepo pickUpPlacesRepo;



    private static final String VECHILE_DIR = "src/main/resources/RC/";


    public ApiResponseModel addVechile(Vechile vechile, User user, MultipartFile file)
    {
        try {
            Optional<Vechile> optionalVechile=vechileRepo.findById(vechile.getVechileNo());

            if(optionalVechile.isPresent())
            {
                return new ApiResponseModel(StatusResponse.failed,null,"Vechile Already exists");
            }

            if(file!=null)
            {
                String path=saveRcImage(file,vechile.getVechileNo());
                vechile.setRc_status(DocumentStatus.updated);
                vechile.setRcPath(path);
            }else{
                vechile.setRc_status(DocumentStatus.not_updated);
            }
            vechile.setUser(user);
            vechile.setAvailable_capacity(vechile.getMax_capacity());
            vechile.setStatus(VechileStatus.pending);
            vechileRepo.save(vechile);
            return new ApiResponseModel<>(StatusResponse.success,null,"Vechile Added");
        }catch (Exception e)
        {
            e.printStackTrace();
            return  new ApiResponseModel<>(StatusResponse.failed,null,"Error in adding vechile");
        }
    }

    public ApiResponseModel<List<Vechile>> findVechileByUserandStatus(User user, VechileStatus vechileStatus)
    {
        try {

            List<Vechile> vechileList = vechileRepo.findVechileByEmailAndStatus(user.getEmailId(), vechileStatus);
            if (vechileList.size() > 0) {
                return new ApiResponseModel<>(StatusResponse.success, vechileList, "vechile list found");
            } else {
                return new ApiResponseModel<>(StatusResponse.not_found, null, "No vechile found");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel<>(StatusResponse.failed,null,"Error in founding vechile");
        }
    }

    public ApiResponseModel findRoutes(String key,String city)
    {
        List<String> sources = routesRepo.searchPlaces(key,city);
        List<String> places = pickUpPlacesRepo.searchPlaces(key,city);
        Set<String> results = new HashSet<>();
        results.addAll(sources);
        results.addAll(places);
        return new ApiResponseModel<>(StatusResponse.success,results,"Places found");
    }

    private String saveRcImage(MultipartFile file, String vechileNo) throws IOException {
        File directory = new File(VECHILE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(VECHILE_DIR + vechileNo + ".jpg");
        Files.write(filePath, file.getBytes());

        return filePath.toAbsolutePath().toString();
    }
}
