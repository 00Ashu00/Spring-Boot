package com.example.hospital_management_system.dto;

import com.example.hospital_management_system.enums.SubscriptionPlan;

public class SubscriptionRequest {
    private SubscriptionPlan plan;

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
}
