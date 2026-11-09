package com.example.hospital_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @GetMapping("/")
    public String home() {
        return "Google OAuth Login Successful!";
    }
}
