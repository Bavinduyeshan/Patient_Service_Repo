package com.healthid_Patients.Healthid_patients;

import org.springframework.context.annotation.Bean;
import feign.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
