package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        switch (user.getRole().toUpperCase()) {
            case "DOCTOR" -> {
                authorities.add(new SimpleGrantedAuthority("DOCTOR_READ"));
                authorities.add(new SimpleGrantedAuthority("DOCTOR_UPDATE"));
            }
            case "ADMIN" -> {
                authorities.add(new SimpleGrantedAuthority("DOCTOR_READ"));
                authorities.add(new SimpleGrantedAuthority("DOCTOR_UPDATE"));
                authorities.add(new SimpleGrantedAuthority("DOCTOR_DELETE"));
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
