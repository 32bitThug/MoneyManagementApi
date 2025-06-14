package com.rs.money.management.api.controller;

import com.rs.money.management.api.services.impl.PasswordResetOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordResetOtpController {
    @Autowired
    PasswordResetOtpService passwordResetOtpService;
    @PostMapping("/password")
    void setPasswordResetOtp(@RequestParam String email){
        passwordResetOtpService.setPasswordResetOtp(email);
    }
}
