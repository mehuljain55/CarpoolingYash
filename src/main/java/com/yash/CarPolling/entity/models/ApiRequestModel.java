package com.yash.CarPolling.entity.models;


import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;

public class ApiRequestModel {

    private User user;
    private String token;
    private Vechile vechile;



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
}
