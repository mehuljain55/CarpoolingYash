package com.yash.CarPolling.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private Routes routes;
    private List<User> users;

    public Bookings(int bookingId, Routes routes, List<User> users) {
        this.bookingId = bookingId;
        this.routes = routes;
        this.users = users;
    }

    public Bookings() {
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
