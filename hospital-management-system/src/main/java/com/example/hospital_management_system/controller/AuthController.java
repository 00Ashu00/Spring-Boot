
package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.dto.SignupResponse;
import com.example.hospital_management_system.entity.User;

import com.example.hospital_management_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;


    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/signup")
    public SignupResponse signUp(@RequestBody User user) {

        User savedUser = authService.signup(
                user.getUsername(),
                user.getPassword()

        );

        return new SignupResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}

