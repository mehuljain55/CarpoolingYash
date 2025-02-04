package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.UserRepo;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private UserRepo userRepo;


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
