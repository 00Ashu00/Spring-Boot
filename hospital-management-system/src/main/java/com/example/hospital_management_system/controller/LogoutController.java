package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.service.TokenRevocationService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    private final TokenRevocationService  tokenRevocationService;
    private LogoutController(TokenRevocationService tokenRevocationService) {
        this.tokenRevocationService = tokenRevocationService;
    }
    @PostMapping("/api/auth/logout")
    public String logout( @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Invalid Authorization header";
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            tokenRevocationService.revokeToken(token);
        }

        return "Logout successful. Please delete your token.";
    }
}
