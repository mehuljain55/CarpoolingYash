 package com.yash.CarPolling.entity.models;


import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.VechileStatus;

 public class ApiRequestModel {

    private User user;
    private String token;
    private Vechile vechile;
    private VechileStatus vechileStatus;

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
 }
