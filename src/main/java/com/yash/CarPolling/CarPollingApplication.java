package com.yash.CarPolling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.yash.CarPolling")
public class CarPollingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarPollingApplication.class, args);
	}

}
