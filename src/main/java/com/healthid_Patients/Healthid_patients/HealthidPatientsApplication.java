package com.healthid_Patients.Healthid_patients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.healthid_Patients.Healthid_patients.service")
public class HealthidPatientsApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthidPatientsApplication.class, args);
	}
}
