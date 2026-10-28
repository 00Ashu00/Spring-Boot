package com.example.hospital_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowserController {

    @GetMapping("/login-success")
    public String loginSuccess() {
        return "Browser login successful!";
    }
}
