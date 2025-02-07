package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Bookings;
import com.yash.CarPolling.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookingRepo extends JpaRepository<Bookings,Integer> {

    @Query("SELECT b FROM Bookings b WHERE b.routes.routeId = :routeId")
    Bookings findBookingsByRouteId(@Param("routeId") int routeId);

    @Query("SELECT b FROM Bookings b WHERE b.bookingId= :bookingId")
    Bookings findBookingsByBookingId(@Param("bookingId") int bookingId);


    @Query("SELECT b.users FROM Bookings b JOIN b.routes r WHERE r.routeId = :routeId")
    List<User> findUsersByRouteId(@Param("routeId") int routeId);

    @Query("SELECT r.routeId FROM Bookings b JOIN b.routes r WHERE b.bookingId = :bookingId")
    Integer findRouteIdByBookingId(@Param("bookingId") int bookingId);
}
