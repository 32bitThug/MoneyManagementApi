package com.rs.money.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rs.money.management.api.services.impl.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public String sendMail(@RequestParam String to) {
        emailService.sendSimpleMail(to, "Test Subject", "This is a test mail!");
        return "Email Sent!";
    }
}