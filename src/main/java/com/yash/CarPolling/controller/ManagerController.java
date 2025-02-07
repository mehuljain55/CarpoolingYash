package com.yash.CarPolling.controller;

import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.enums.UserRoles;
import com.yash.CarPolling.entity.models.ApiRequestModel;
import com.yash.CarPolling.entity.models.ApiRequestModelUpdateStatus;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.service.ManagerService;
import com.yash.CarPolling.service.UserAuthorizationService;
import com.yash.CarPolling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserAuthorizationService userAuthorizationService;


    private final UserRoles role=UserRoles.admin;

    @PostMapping("/verifyVechile")
    public ApiResponseModel getVechileListApproval(@RequestBody ApiRequestModel apiRequestModel)
    {
     boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(),role);
     if(status)
     {
       return managerService.getUserVechileListPending(apiRequestModel.getOfficeId());
     }else {
         return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized Access");
     }
    }


    @PostMapping("/updateVechileStatus")
    public ApiResponseModel updateVechileStatus(@RequestBody ApiRequestModelUpdateStatus apiRequestModelUpdateStatus)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModelUpdateStatus.getUser(),apiRequestModelUpdateStatus.getToken(),role);
        if(status)
        {
            return managerService.updateVechileStatus(apiRequestModelUpdateStatus.getVechileNo(), apiRequestModelUpdateStatus.getVechileStatus());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized Access");
        }
    }



    @PostMapping("/userApprovalList")
    public ApiResponseModel userApproval(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(),role);
        if(status)
        {
            return managerService.getUserListPending(apiRequestModel.getUser().getOfficeId());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized Access");
        }
    }

    @PostMapping("/updateUserStatus")
    public ApiResponseModel updateUserStatus(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(),role);
        if(status)
        {
            return managerService.updateUserStatus(apiRequestModel.getEmailId(), apiRequestModel.getStatus());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized Access");
        }
    }

    }



