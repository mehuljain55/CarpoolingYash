package com.yash.CarPolling.controller;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.enums.VechileStatus;
import com.yash.CarPolling.entity.models.ApiRequestModel;
import com.yash.CarPolling.entity.models.ApiRequestModelBooking;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.entity.models.UserLoginModel;
import com.yash.CarPolling.service.*;
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

    @Autowired
    private OfficeService officeService;

    @Autowired
    private VechileService vechileService;

    @Autowired
    private BookingService bookingService;


    @PostMapping("/register")
    public ApiResponseModel<String> userRegistration(@RequestPart("user") User user,
                                                     @RequestPart("officeId") String officeId,
                                                     @RequestPart(value = "licenceImage", required = false) MultipartFile licenceImage){
        return userService.addUser(user,officeId,licenceImage);
    }

    @GetMapping("/login")
    public ApiResponseModel validateUserLogin(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
        return userAuthorizationService.validateUserLogin(emailId, password);
    }

    @PostMapping("/updateLicence")
    public ApiResponseModel<UserLoginModel> updateLicence(@RequestPart("user") User user,
                                                          @RequestPart("licenceNo") String licenceNo,
                                                          @RequestPart("token") String token,
                                                          @RequestPart(value = "licenceImage", required = true) MultipartFile licenceImage){

        boolean status=userAuthorizationService.validateUserToken(user.getEmailId(),token);
        if (status) {
            return userService.updateLicence(user.getEmailId(),licenceNo,licenceImage);
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }




    @GetMapping ("/validate_token")
    public ApiResponseModel validateUserToken(@RequestParam("userId") String userId,@RequestParam("token") String token)
    {
        boolean status=userAuthorizationService.validateUserToken(userId,token);
        ApiResponseModel apiResponseModel;
        if(status)
        {
            apiResponseModel=new ApiResponseModel<>(StatusResponse.authorized,null,"Valid token");
        }
        else {
            apiResponseModel=new ApiResponseModel<>(StatusResponse.unauthorized,null,"Invalid token");
        }

        return apiResponseModel;
    }


    @GetMapping("/officeList")
    public ApiResponseModel validateUserLogin() {
        return officeService.officeList();
    }

    @PostMapping("/createBooking")
    public ApiResponseModel createBooking(@RequestBody ApiRequestModelBooking apiRequestModelBooking) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModelBooking.getUser().getEmailId(),apiRequestModelBooking.getToken());
        if(status)
        {
            return  bookingService.createBooking(apiRequestModelBooking.getRouteId(),apiRequestModelBooking.getUser());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/findBookingRequest")
    public ApiResponseModel findBookingRequest(@RequestBody ApiRequestModel apiRequestModel) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());
        if(status)
        {
            return  bookingService.findBookingRequest(apiRequestModel.getUser());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/updateBookingRequest")
    public ApiResponseModel updateBookingRequest(@RequestBody ApiRequestModel apiRequestModel) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());
        if(status)
        {
            return  bookingService.findBookingRequest(apiRequestModel.getUser());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/getVechileList")
    public ApiResponseModel getVechileList(@RequestBody ApiRequestModel apiRequestModel) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());

        if(status)
        {
            return  vechileService.findVechileByUserandStatus(apiRequestModel.getUser(), VechileStatus.approved);
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/getBuddiesInfo")
    public ApiResponseModel getBuddiesInfo(@RequestBody ApiRequestModel apiRequestModel) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());

        if(status)
        {
            return  bookingService.getBuddiesInformation(apiRequestModel.getUser().getEmailId());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/findMyBookings")
    public ApiResponseModel findMyBookings(@RequestBody ApiRequestModel apiRequestModel) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());

        if(status) {

            return bookingService.findMyBooking(apiRequestModel.getUser().getEmailId());

        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/cancelBooking")
    public ApiResponseModel cancelBooking(@RequestBody ApiRequestModel apiRequestModel) {

        boolean status=userAuthorizationService.validateUserToken(apiRequestModel.getUser().getEmailId(),apiRequestModel.getToken());

        if(status)
        {
            return  bookingService.cancelBooking(apiRequestModel.getUser().getEmailId());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }



}
