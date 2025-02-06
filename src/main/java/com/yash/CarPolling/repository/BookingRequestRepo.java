package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepo extends JpaRepository<BookingRequest,Integer> {
}
