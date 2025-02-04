package com.yash.CarPolling.entity.models;

import com.yash.CarPolling.entity.User;

public class ApiRequestModelBooking {

    private User user;
    private String token;
    private int routeId;

    public ApiRequestModelBooking(User user, String token, int routeId) {
        this.user = user;
        this.token = token;
        this.routeId = routeId;
    }

    public ApiRequestModelBooking() {
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

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
