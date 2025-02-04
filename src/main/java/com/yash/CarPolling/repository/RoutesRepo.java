package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Routes;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.RouteStatus;
import com.yash.CarPolling.entity.enums.VechileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.util.RouteMatcher;

import java.util.List;

public interface RoutesRepo extends JpaRepository<Routes,Integer> {


    @Query("SELECT r FROM Routes r JOIN r.pickUpPlaces p WHERE p.places = :places AND r.destination = :destination")
    List<Routes> findRouteBySourceDestination(@Param("places") String places, @Param("destination") String destination);


    @Query("SELECT r.vechile FROM Routes r WHERE r.routeId=:routeId")
    Vechile findVechileByRouteNo(@Param("routeId") int routeId);



}
