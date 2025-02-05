package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficeRepo extends JpaRepository<Office,String> {

    @Query("SELECT DISTINCT o.city FROM Office o")
    List<String> findDistinctCities();
}
