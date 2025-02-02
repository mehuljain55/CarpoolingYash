package com.yash.CarPolling.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="pick_up_point")
public class PickUpPlaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pickupId;
    private String places;

    @Temporal(TemporalType.TIME)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Routes routes;

    public PickUpPlaces(int pickupId, String places, Date time, Routes routes) {
        this.pickupId = pickupId;
        this.places = places;
        this.time = time;
        this.routes = routes;
    }

    public PickUpPlaces() {
    }

    public int getPickupId() {
        return pickupId;
    }

    public void setPickupId(int pickupId) {
        this.pickupId = pickupId;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }
}
