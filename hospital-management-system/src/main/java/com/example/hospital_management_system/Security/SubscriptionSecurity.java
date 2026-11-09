package com.example.hospital_management_system.Security;

import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.enums.SubscriptionPlan;
import com.example.hospital_management_system.repository.UserRepository;
import com.example.hospital_management_system.service.SubscriptionService;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionSecurity {

    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;

    public SubscriptionSecurity(
            UserRepository userRepository,
            SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
    }

    public boolean hasAtLeastPlan(String username, String requiredPlan) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SubscriptionPlan currentPlan =
                subscriptionService.getUserPlan(user.getId());

        return currentPlan.ordinal()
                >= SubscriptionPlan.valueOf(requiredPlan).ordinal();
    }
}
