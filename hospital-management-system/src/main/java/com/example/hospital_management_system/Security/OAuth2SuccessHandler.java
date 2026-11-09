package com.example.hospital_management_system.Security;

import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.repository.UserRepository;
import com.example.hospital_management_system.service.JwtService;
import com.example.hospital_management_system.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SessionService sessionService;

    public OAuth2SuccessHandler(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            SessionService sessionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.sessionService = sessionService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");

        User user = userRepository.findByUsername(email)
                .orElseGet(() -> userRepository.save(
                        new User(
                                email,
                                passwordEncoder.encode(UUID.randomUUID().toString()),
                                "USER"
                        )
                ));

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        sessionService.saveSession(user.getUsername(), accessToken, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                "{\"accessToken\":\"" + accessToken + "\"}"
        );
    }
}
