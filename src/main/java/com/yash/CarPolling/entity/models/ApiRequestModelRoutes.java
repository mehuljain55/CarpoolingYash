package com.yash.CarPolling.entity.models;

import com.yash.CarPolling.entity.Routes;
import com.yash.CarPolling.entity.User;

public class ApiRequestModelRoutes {
    private User user;
    private String token;
    private Routes routes;
    private String vechileNo;

    public ApiRequestModelRoutes(User user, String token, Routes routes, String vechileNo) {
        this.user = user;
        this.token = token;
        this.routes = routes;
        this.vechileNo = vechileNo;
    }

    public ApiRequestModelRoutes() {
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

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public String getVechileNo() {
        return vechileNo;
    }

    public void setVechileNo(String vechileNo) {
        this.vechileNo = vechileNo;
    }
}
