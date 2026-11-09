
package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.dto.LoginRequest;
import com.example.hospital_management_system.dto.SignupResponse;
import com.example.hospital_management_system.entity.User;

import com.example.hospital_management_system.service.AuthService;
import com.example.hospital_management_system.service.JwtService;
import com.example.hospital_management_system.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.username(),
                                loginRequest.password()
                        )
                );

       String accessToken= jwtService.generateAccessToken(authentication.getName());
       String refreshToken=jwtService.generateRefreshToken(authentication.getName());
        sessionService.saveSession(authentication.getName(), accessToken, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(accessToken);
    }
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request) {

        String refreshToken = null;


        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null ||
                !jwtService.isRefreshToken(refreshToken) ||
                !sessionService.isRefreshTokenActive(refreshToken)) {

            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);

        String newAccessToken = jwtService.generateAccessToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        sessionService.updateTokens(refreshToken, newAccessToken, newRefreshToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(newAccessToken);
    }


}

