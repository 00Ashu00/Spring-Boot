package com.example.hospital_management_system.service;


import com.example.hospital_management_system.entity.SessionEntity;
import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.repository.SessionRepository;
import com.example.hospital_management_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, JwtService jwtService, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    public boolean isTokenActive(String token) {
        return sessionRepository.findByToken(token).isPresent();
    }
    public void removeSession(String token) {
        sessionRepository.findByToken(token)
                .ifPresent(sessionRepository::delete);
    }
    @Transactional
    public  void replaceSession(String username, String token){
        User user=userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        sessionRepository.deleteByUserId(user.getId());
        sessionRepository.flush();
        SessionEntity session = new SessionEntity(

                token,
                user.getId()
        );

        sessionRepository.save(session);

    }

    }
