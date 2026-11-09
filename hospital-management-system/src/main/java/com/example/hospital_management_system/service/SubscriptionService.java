package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.entity.UserSubscription;
import com.example.hospital_management_system.enums.SubscriptionPlan;
import com.example.hospital_management_system.repository.UserRepository;
import com.example.hospital_management_system.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final UserSubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(
            UserSubscriptionRepository subscriptionRepository,
            UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public void assignPlan(Long userId, SubscriptionPlan plan) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserSubscription subscription = subscriptionRepository
                .findByUserId(userId)
                .orElse(new UserSubscription());

        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setActive(true);

        subscriptionRepository.save(subscription);
    }

    public SubscriptionPlan getUserPlan(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getPlan)
                .orElse(SubscriptionPlan.FREE);
    }
}
