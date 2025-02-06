package com.yash.CarPolling.entity.models;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.VechileStatus;

public class ApiRequestModelUpdateStatus {

    private User user;
    private String token;
    private String vechileNo;
    private VechileStatus vechileStatus;

    public ApiRequestModelUpdateStatus(User user, String token, VechileStatus vechileStatus) {
        this.user = user;
        this.token = token;
        this.vechileStatus = vechileStatus;
    }

    public ApiRequestModelUpdateStatus() {
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

    public VechileStatus getVechileStatus() {
        return vechileStatus;
    }

    public void setVechileStatus(VechileStatus vechileStatus) {
        this.vechileStatus = vechileStatus;
    }

    public String getVechileNo() {
        return vechileNo;
    }

    public void setVechileNo(String vechileNo) {
        this.vechileNo = vechileNo;
    }
}
