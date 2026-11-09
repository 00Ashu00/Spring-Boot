package com.example.hospital_management_system.entity;

import com.example.hospital_management_system.enums.SubscriptionPlan;
import jakarta.persistence.*;

@Entity
@Table(name = "user_subscriptions")
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    private boolean active = true;
    public UserSubscription() {
    }
    public UserSubscription(User user, SubscriptionPlan plan, boolean active) {
        this.user = user;
        this.plan = plan;
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}