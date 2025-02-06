package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.VechileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VechileRepo extends JpaRepository<Vechile,String> {

    @Query("SELECT v FROM Vechile v WHERE v.user.emailId = :emailId AND v.status = :status")
    List<Vechile> findVechileByEmailAndStatus(@Param("emailId") String emailId, @Param("status") VechileStatus status);

    @Query("SELECT v FROM Vechile v WHERE v.user.officeId = :officeId AND v.rc_status=:rc_status AND v.status = :status")
    List<Vechile> findVechileDocumentStatus(@Param("rc_status") DocumentStatus rc_status, @Param("status") VechileStatus status,@Param("officeId") String officeId);


    @Query("SELECT v.user FROM Vechile v WHERE v.vechileNo = :vechileNo")
    User findUserByVechileNo(String vechileNo);



}
