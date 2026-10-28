
package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.dto.LoginRequest;
import com.example.hospital_management_system.dto.SignupResponse;
import com.example.hospital_management_system.entity.User;

import com.example.hospital_management_system.service.AuthService;
import com.example.hospital_management_system.service.JwtService;
import com.example.hospital_management_system.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager  authenticationManager;
    private JwtService jwtService;
    private SessionService sessionService;



    @Autowired
    private void setJwtService (JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    private AuthController(AuthenticationManager authenticationManager,SessionService sessionService){
        this.authenticationManager = authenticationManager;
        this.sessionService = sessionService;
    }


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

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.username(),
                                loginRequest.password()
                        )
                );

       String token= jwtService.generateToken(authentication.getName());
        sessionService.replaceSession(authentication.getName(), token);
        return token;
    }


}

