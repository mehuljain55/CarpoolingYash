package com.yash.CarPolling.controller;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @PostMapping("/register")
    public ApiResponseModel<String> userRegistration(@RequestBody User user){
        return userAuthorizationService.addUser(user);
    }

    @GetMapping("/validateLogin")
    public ApiResponseModel validateUserLogin(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
        return userAuthorizationService.validateUserLogin(emailId, password);
    }


}
