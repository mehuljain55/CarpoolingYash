 package com.yash.CarPolling.entity.models;


import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.RequestStatus;
import com.yash.CarPolling.entity.enums.UserStatus;
import com.yash.CarPolling.entity.enums.VechileStatus;

 public class ApiRequestModel {

    private User user;
    private String token;
    private Vechile vechile;
    private VechileStatus vechileStatus;
    private String officeId;
    private int bookingRequestId;
    private RequestStatus requestStatus;
    private UserStatus status;
    private String emailId;

     public ApiRequestModel(User user, String token, Vechile vechile, VechileStatus vechileStatus) {
         this.user = user;
         this.token = token;
         this.vechile = vechile;
         this.vechileStatus = vechileStatus;
     }

     public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Vechile getVechile() {
        return vechile;
    }

    public void setVechile(Vechile vechile) {
        this.vechile = vechile;
    }

     public VechileStatus getVechileStatus() {
         return vechileStatus;
     }

     public void setVechileStatus(VechileStatus vechileStatus) {
         this.vechileStatus = vechileStatus;
     }

     public String getOfficeId() {
         return officeId;
     }

     public void setOfficeId(String officeId) {
         this.officeId = officeId;
     }

     public int getBookingRequestId() {
         return bookingRequestId;
     }

     public void setBookingRequestId(int bookingRequestId) {
         this.bookingRequestId = bookingRequestId;
     }

     public RequestStatus getRequestStatus() {
         return requestStatus;
     }

     public void setRequestStatus(RequestStatus requestStatus) {
         this.requestStatus = requestStatus;
     }

     public UserStatus getStatus() {
         return status;
     }

     public void setStatus(UserStatus status) {
         this.status = status;
     }

     public String getEmailId() {
         return emailId;
     }

     public void setEmailId(String emailId) {
         this.emailId = emailId;
     }
 }
