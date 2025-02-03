package com.yash.CarPolling.controller;


import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.models.ApiRequestModel;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.service.UserAuthorizationService;
import com.yash.CarPolling.service.VechileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vechile")
public class VechileController {

    @Autowired
    private VechileService vechileService;

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @PostMapping("/addVechile")
    public ApiResponseModel addVechile(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());

        if(status)
        {
            return  vechileService.addVechile(apiRequestModel.getVechile(),apiRequestModel.getUser());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

}
