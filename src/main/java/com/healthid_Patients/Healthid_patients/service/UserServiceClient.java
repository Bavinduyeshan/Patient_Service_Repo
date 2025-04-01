package com.healthid_Patients.Healthid_patients.service;

import com.healthid_Patients.Healthid_patients.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", url = "${USER_SERVICE_URL:user-service-url}")
public interface UserServiceClient {
    @GetMapping("/validate/{userId}")
    UserResponse validateUser(@PathVariable int userId);
}
