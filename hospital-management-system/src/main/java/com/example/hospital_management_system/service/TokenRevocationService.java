package com.example.hospital_management_system.service;


import com.example.hospital_management_system.entity.RevokedToken;
import com.example.hospital_management_system.repository.RevokedTokenRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import com.example.hospital_management_system.entity.RevokedToken;
import com.example.hospital_management_system.repository.RevokedTokenRepository;
import com.example.hospital_management_system.service.JwtService;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenRevocationService {
    private final RevokedTokenRepository revokedTokenRepository;
    private final JwtService jwtService;

    public TokenRevocationService(RevokedTokenRepository revokedTokenRepository,JwtService jwtService) {
        this.revokedTokenRepository = revokedTokenRepository;
        this.jwtService = jwtService;
    }
    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }
    public boolean isTokenRevoked(String token) {
        String tokenHash = hashToken(token);
        return revokedTokenRepository.existsByTokenHash(tokenHash);
    }
    public void revokeToken(String token) {
        String tokenHash = hashToken(token);

        Date expirationDate = jwtService.extractExpiration(token);
        Instant expiryTime = expirationDate.toInstant();

        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setTokenHash(tokenHash);
        revokedToken.setExpiryTime(expiryTime);

        revokedTokenRepository.save(revokedToken);
    }

}
