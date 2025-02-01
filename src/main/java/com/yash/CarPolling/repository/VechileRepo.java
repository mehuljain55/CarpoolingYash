package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.VechileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VechileRepo extends JpaRepository<Vechile,String> {

    @Query("SELECT v FROM Vechile v WHERE v.user.emailId = :emailId AND v.status = :status")
    List<Vechile> findVechileByEmailAndStatus(@Param("emailId") String emailId, @Param("status") VechileStatus status);



}
