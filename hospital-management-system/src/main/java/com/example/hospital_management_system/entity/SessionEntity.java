package com.example.hospital_management_system.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_sessions",
        uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String token;

    public SessionEntity( String token, Long userId) {

        this.token = token;
        this.userId = userId;
    }
    public SessionEntity() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
