
package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.SessionEntity;
import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.repository.SessionRepository;
import com.example.hospital_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public boolean isRefreshTokenActive(String refreshToken) {
        return sessionRepository.findByRefreshToken(refreshToken).isPresent();
    }

    public List<SessionEntity> getUserSessions(Long userId) {
        return sessionRepository.findAllByUserId(userId);
    }

    @Transactional
    public void saveSession(String username, String refreshToken) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SessionEntity session = new SessionEntity(
                user.getId(),
                refreshToken,
                Instant.now()
        );

        sessionRepository.save(session);
    }
    @Transactional
    public void updateRefreshToken(String oldToken, String newToken) {
        SessionEntity session = sessionRepository.findByRefreshToken(oldToken)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setRefreshToken(newToken);
        session.setLastUsedAt(Instant.now());

        sessionRepository.save(session);
    }
    public void removeSession(String refreshToken) {
        sessionRepository.findByRefreshToken(refreshToken)
                .ifPresent(sessionRepository::delete);
    }
    @Transactional
    public void replaceUserSession(String username, String refreshToken) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SessionEntity> sessions =
                sessionRepository.findAllByUserId(user.getId());

        if (sessions.isEmpty()) {
            saveSession(username, refreshToken);
        } else {
            SessionEntity session = sessions.get(0);

            session.setRefreshToken(refreshToken);
            session.setLastUsedAt(Instant.now());

            sessionRepository.save(session);
        }
    }
}

