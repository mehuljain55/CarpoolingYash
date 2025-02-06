package com.yash.CarPolling.controller;

import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.models.ApiRequestModelRoutes;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.service.OfficeService;
import com.yash.CarPolling.service.UserAuthorizationService;
import com.yash.CarPolling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RoutesController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private OfficeService officeService;


    @PostMapping("/addRoute")
    public ApiResponseModel addRoute(@RequestBody ApiRequestModelRoutes apiRequestModelRoutes) {
        System.out.println(apiRequestModelRoutes);
        boolean status=userAuthorizationService.validateUserToken(apiRequestModelRoutes.getUser().getEmailId(),apiRequestModelRoutes.getToken());
        if(status)
        {
            return  userService.addRoutes(apiRequestModelRoutes.getUser(),apiRequestModelRoutes.getRoutes(),apiRequestModelRoutes.getVechileNo(),apiRequestModelRoutes.getCity());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @GetMapping("/findRoutes")
    public ApiResponseModel findRoutes(@RequestParam("source") String source,
                                       @RequestParam("destination") String destination,
                                       @RequestParam("city") String city) {

        return  userService.findRoutes(source,destination,city);
    }

    @GetMapping("/cityList")
    public ApiResponseModel findCityList()
    {
        return userService.cityList();
    }


}
