package com.sample.otp.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class OtpService {

    @Cacheable(value = "OtpCache", key = "#key")
    public String generateOtp(String key) {
        // Generate a random 6-digit OTP

        // You may want to send the OTP via email, SMS, etc.

        return String.format("%06d", new Random().nextInt(1000000));
    }

//    public boolean validateOtp(String key, String enteredOtp) {
//        // Retrieve the cached OTP from Redis
//        String cachedOtp = generateOtp(key);
//
//        // Validate the entered OTP
//        return enteredOtp.equals(cachedOtp);
//    }
}
