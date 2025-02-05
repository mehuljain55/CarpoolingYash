package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookingRepo extends JpaRepository<Bookings,Integer> {

    @Query("SELECT b FROM Bookings b WHERE b.routes.routeId = :routeId")
    Bookings findBookingsByRouteId(@Param("routeId") int routeId);
}
