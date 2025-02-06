package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, String> {

    @Query("SELECT b.bookingId FROM Bookings b JOIN b.users u WHERE u.emailId = :emailId")
    Integer findBookingIdByEmailId(@Param("emailId") String emailId);

}
