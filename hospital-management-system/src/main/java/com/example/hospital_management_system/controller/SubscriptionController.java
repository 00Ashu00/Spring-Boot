package com.example.hospital_management_system.controller;


import com.example.hospital_management_system.dto.SubscriptionRequest;
import com.example.hospital_management_system.enums.SubscriptionPlan;
import com.example.hospital_management_system.repository.UserSubscriptionRepository;

import com.example.hospital_management_system.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> assignPlan(
            @PathVariable Long userId,
            @RequestBody SubscriptionRequest request) {

        subscriptionService.assignPlan(userId, request.getPlan());
        return ResponseEntity.ok("Subscription plan updated");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SubscriptionPlan> getPlan(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserPlan(userId));
    }
}
