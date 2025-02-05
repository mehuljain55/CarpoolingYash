package com.yash.CarPolling.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name="pick_up_point")
public class PickUpPlaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pickupId;
    private String places;
    private String city;

    private String time;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonBackReference
    private Routes routes;

    public PickUpPlaces(int pickupId, String places, String time, Routes routes) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
