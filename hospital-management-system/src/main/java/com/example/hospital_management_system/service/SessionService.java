
package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.SessionEntity;
import com.example.hospital_management_system.entity.User;
import com.example.hospital_management_system.repository.SessionRepository;
import com.example.hospital_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
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

    public boolean isAccessTokenActive(String accessToken) {
        return sessionRepository.existsByAccessToken(accessToken);
    }

    public List<SessionEntity> getUserSessions(Long userId) {
        return sessionRepository.findAllByUserId(userId);
    }

    @Transactional
    public void saveSession(String username, String accessToken, String refreshToken) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SessionEntity> sessions =
                sessionRepository.findAllByUserId(user.getId());

        int limit = user.getSessionLimitCount();

        if (limit < 1) {
            throw new IllegalStateException("Session limit must be at least 1");
        }

        sessions.sort(Comparator.comparing(
                SessionEntity::getLastUsedAt,
                Comparator.nullsFirst(Comparator.naturalOrder())
        ));

        while (sessions.size() >= limit) {
            SessionEntity oldestSession = sessions.remove(0);
            sessionRepository.delete(oldestSession);
        }

        SessionEntity session = new SessionEntity(
                user.getId(),
                accessToken,
                refreshToken,
                Instant.now()
        );

        sessionRepository.save(session);
    }

    @Transactional
    public void updateTokens(String oldRefreshToken,
                             String newAccessToken,
                             String newRefreshToken) {

        SessionEntity session = sessionRepository.findByRefreshToken(oldRefreshToken)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setAccessToken(newAccessToken);
        session.setRefreshToken(newRefreshToken);
        session.setLastUsedAt(Instant.now());

        sessionRepository.save(session);
    }

    public void removeSession(String refreshToken) {
        sessionRepository.findByRefreshToken(refreshToken)
                .ifPresent(sessionRepository::delete);
    }
}


