package com.yash.CarPolling.repository;

import com.yash.CarPolling.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
