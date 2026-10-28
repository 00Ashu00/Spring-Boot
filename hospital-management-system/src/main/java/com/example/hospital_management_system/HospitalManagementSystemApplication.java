package com.example.hospital_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class HospitalManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementSystemApplication.class, args);

		String secret = System.getenv("JWT_SECRET");
		System.out.println("JWT_SECRET configured: "
				+ (secret != null && !secret.isBlank()));
	}

}
