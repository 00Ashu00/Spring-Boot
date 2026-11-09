package com.example.hospital_management_system.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    private Instant lastUsedAt;
    @Column(name = "access_token", nullable = false)
    private String accessToken;

    public String getAccessToken() {
        return accessToken;

    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public SessionEntity() {
    }

    public SessionEntity(Long userId, String accessToken,
                         String refreshToken, Instant lastUsedAt) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.lastUsedAt = lastUsedAt;
    }
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }
}

