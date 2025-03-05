package com.yash.CarPolling.controller;


import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.models.ApiRequestModel;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.service.UserAuthorizationService;
import com.yash.CarPolling.service.VechileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/vechile")
public class VechileController {

    @Autowired
    private VechileService vechileService;

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @PostMapping("/addVechile")
    public ApiResponseModel addVechile(@RequestPart("request") ApiRequestModel apiRequestModel,
                                       @RequestPart(value = "vechileImage", required = false) MultipartFile vechileImage)
    {
        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(), apiRequestModel.getToken());

        if(status)
        {
            return  vechileService.addVechile(apiRequestModel.getVechile(),apiRequestModel.getUser(),vechileImage);
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/findVechileByUserandStatus")
    public ApiResponseModel findVechileByUserandStatus(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());
        if(status)
        {
            return  vechileService.findVechileByUserandStatus(apiRequestModel.getUser(),apiRequestModel.getVechileStatus());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

}
