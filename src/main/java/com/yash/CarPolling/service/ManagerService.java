package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.enums.UserStatus;
import com.yash.CarPolling.entity.enums.VechileStatus;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.UserRepo;
import com.yash.CarPolling.repository.VechileRepo;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VechileRepo vechileRepo;


    public ApiResponseModel getUserVechileListPending(String officeId) {
        List<Vechile> vechileList = vechileRepo.findVechileDocumentStatus(DocumentStatus.updated, VechileStatus.pending, officeId);
        List<Vechile> finalVechileList = new ArrayList<>();

        for (Vechile vechile : vechileList) {
            byte[] imageBytes = getVehicleImageBytes(vechile.getRcPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            vechile.setImage(base64Image);
            finalVechileList.add(vechile);
        }

        return new ApiResponseModel<>(StatusResponse.success, finalVechileList, "VechileList found");
    }


    public ApiResponseModel updateUserStatus(String email,UserStatus status) {
       Optional<User> userOptional=userRepo.findById(email);
       if(userOptional.isPresent()) {
           User user = userOptional.get();
           user.setStatus(status);
           userRepo.save(user);
           return new ApiResponseModel<>(StatusResponse.success, null, "Status Updated");
       }else {
           return new ApiResponseModel<>(StatusResponse.failed,null,"User not found");
       }
    }


    public ApiResponseModel getUserListPending(String officeId) {
        List<User> userList = userRepo.findUserByStatusandOffice(UserStatus.not_active,officeId);
        return new ApiResponseModel<>(StatusResponse.success, userList, "User List found");
    }


    public ApiResponseModel updateVechileStatus(String vechileNo,VechileStatus vechileStatus) {
        Optional<Vechile> optionalVechile=vechileRepo.findById(vechileNo);
        if(optionalVechile.isPresent())
        {
            Vechile vechile=optionalVechile.get();
            vechile.setStatus(vechileStatus);
            vechileRepo.save(vechile);
            return new ApiResponseModel<>(StatusResponse.success,null,"Status Updates");
        }else{
          return new ApiResponseModel<>(StatusResponse.failed,null,"Vechile not found");
        }


    }




    private byte[] getVehicleImageBytes(String imagePath) {
        try {
            File file = new File(imagePath);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }



}


