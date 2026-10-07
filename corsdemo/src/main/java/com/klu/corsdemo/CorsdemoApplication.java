package com.klu.corsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorsdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorsdemoApplication.class, args);
		System.out.println("Application is running on port 8081");
	}

}
