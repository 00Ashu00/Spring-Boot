package com.example.hospital_management_system.config;

import com.example.hospital_management_system.service.CustomUserDetailsService;
import com.example.hospital_management_system.service.JwtService;
import com.example.hospital_management_system.service.SessionService;
import com.example.hospital_management_system.service.TokenRevocationService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

    @Component
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final CustomUserDetailsService userDetailsService;
        private final TokenRevocationService tokenRevocationService;
        private final SessionService sessionService;
        public JwtAuthenticationFilter(
                JwtService jwtService,
                CustomUserDetailsService userDetailsService, TokenRevocationService tokenRevocationService, SessionService sessionService) {
            this.jwtService = jwtService;
            this.userDetailsService = userDetailsService;
            this.tokenRevocationService = tokenRevocationService;
            this.sessionService =sessionService;
        }

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain)
                throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(7);
            try {
                String username = jwtService.extractUsername(token);
                if (!sessionService.isTokenActive(token)) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (username == null || username.isBlank()) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (tokenRevocationService.isTokenRevoked(token)) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);


                if (SecurityContextHolder.getContext().getAuthentication() == null
                        && jwtService.isTokenValid(token, userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
         catch (JwtException | IllegalArgumentException | UsernameNotFoundException ex) {
             SecurityContextHolder.clearContext();
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             return;
        }
            filterChain.doFilter(request, response);
        }
    }

