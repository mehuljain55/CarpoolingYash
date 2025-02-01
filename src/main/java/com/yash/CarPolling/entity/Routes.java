package com.yash.CarPolling.entity;

import com.yash.CarPolling.entity.enums.RouteStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="routes")
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;
    private Vechile vechile;
    private User user;
    private String source;


    @OneToMany(mappedBy = "routes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PickUpPlaces> pickUpPlaces;


    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String destination;

    @Enumerated(EnumType.STRING)
    private RouteStatus status;

    private int cost;

    public Routes(int routeId, Vechile vechile, User user, String source, List<PickUpPlaces> pickUpPlaces, Date startDate, Date endDate, String destination, RouteStatus status) {
        this.routeId = routeId;
        this.vechile = vechile;
        this.user = user;
        this.source = source;
        this.pickUpPlaces = pickUpPlaces;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.status = status;
    }

    public Routes() {
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Vechile getVechile() {
        return vechile;
    }

    public void setVechile(Vechile vechile) {
        this.vechile = vechile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<PickUpPlaces> getPickUpPlaces() {
        return pickUpPlaces;
    }

    public void setPickUpPlaces(List<PickUpPlaces> pickUpPlaces) {
        this.pickUpPlaces = pickUpPlaces;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }

    public int getCost() { 
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
