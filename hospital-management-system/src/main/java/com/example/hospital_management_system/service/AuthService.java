package com.example.hospital_management_system.service;


import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(String username, String rawPassword) {
        User user = new User(
                username,
                passwordEncoder.encode(rawPassword),
                "DOCTOR"
        );
        return userRepository.save(user);

    }
    public void updateRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(role);
        userRepository.save(user);
    }
}
