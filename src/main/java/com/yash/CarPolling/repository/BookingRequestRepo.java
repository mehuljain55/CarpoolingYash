package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.BookingRequest;
import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookingRequestRepo extends JpaRepository<BookingRequest,Integer> {

    @Query("SELECT b FROM BookingRequest b where b.ownerEmployeeId=:ownerEmployeeId AND b.requestStatus=:requestStatus")
    List<BookingRequest> findBookingRequestOwner(@Param("ownerEmployeeId") String ownerEmployeeId, @Param("requestStatus")RequestStatus requestStatus);

    @Query("SELECT b FROM BookingRequest b where b.employeeID=:employeeID AND b.routeId=:routeId AND b.requestStatus=:requestStatus")
    List<BookingRequest> findBookingRequestEmployeeandRouteId(@Param("employeeID") String employeeID,
                                                              @Param("routeId") int routeId,
                                                              @Param("requestStatus")RequestStatus requestStatus);


}
