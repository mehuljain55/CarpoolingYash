package com.yash.CarPolling.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bookingHistory")
public class BookingHistory {

    @Id
    private int bookingHistoryId;
    private int routeId;
    private int vechileId;
    private int userId;


    public BookingHistory(int bookingHistoryId, int routeId, int vechileId) {
        this.bookingHistoryId = bookingHistoryId;
        this.routeId = routeId;
        this.vechileId = vechileId;
    }

    public BookingHistory() {
    }

    public int getBookingHistoryId() {
        return bookingHistoryId;
    }

    public void setBookingHistoryId(int bookingHistoryId) {
        this.bookingHistoryId = bookingHistoryId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getVechileId() {
        return vechileId;
    }

    public void setVechileId(int vechileId) {
        this.vechileId = vechileId;
    }
}
