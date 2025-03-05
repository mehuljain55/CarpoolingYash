package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Bookings;
import com.yash.CarPolling.entity.PickUpPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PickUpPlacesRepo extends JpaRepository<PickUpPlaces,Integer> {

    @Query("SELECT p FROM PickUpPlaces p WHERE p.routes.routeId = :routeId")
    List<PickUpPlaces> findPlacesByRouteId(@Param("routeId") int routeId);

    @Query("SELECT p.places FROM PickUpPlaces p WHERE  p.city=:city AND UPPER(p.places) LIKE CONCAT('%', UPPER(:search), '%')")
    List<String> searchPlaces(@Param("search") String search,@Param("city") String city);

}
