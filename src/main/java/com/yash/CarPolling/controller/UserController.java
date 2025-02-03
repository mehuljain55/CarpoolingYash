package com.yash.CarPolling.controller;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.service.UserAuthorizationService;
import com.yash.CarPolling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponseModel<String> userRegistration(@RequestPart("user") User user,
                                                     @RequestPart(value = "licenceImage", required = false) MultipartFile licenceImage){
        return userService.addUser(user,licenceImage);
    }

    @GetMapping("/validateLogin")
    public ApiResponseModel validateUserLogin(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
        return userAuthorizationService.validateUserLogin(emailId, password);
    }


}
