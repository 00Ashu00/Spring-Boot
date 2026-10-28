package com.example.hospital_management_system.entity;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "revoked_tokens")
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String tokenHash;

    private Instant expiryTime;

    public RevokedToken(long id, String tokenHash, Instant expiryTime) {
        this.id = id;
        this.tokenHash = tokenHash;
        this.expiryTime = expiryTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }
    public RevokedToken() {
    }
}
