package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Bookings;
import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.UserStatus;
import com.yash.CarPolling.entity.enums.VechileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {

    @Query("SELECT b.bookingId FROM Bookings b JOIN b.users u WHERE u.emailId = :emailId")
    Integer findBookingIdByEmailId(@Param("emailId") String emailId);

    @Query("SELECT b FROM Bookings b JOIN b.users u WHERE u.emailId = :emailId")
    Bookings findBookingByEmailId(@Param("emailId") String emailId);

    @Query("SELECT u FROM User u WHERE u.officeId = :officeId AND u.status=:status")
    List<User> findUserByStatusandOffice(@Param("status") UserStatus status, @Param("officeId") String officeId);



}
