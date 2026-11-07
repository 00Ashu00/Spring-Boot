
package com.example.hospital_management_system.repository;

import com.example.hospital_management_system.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByRefreshToken(String refreshToken);

    List<SessionEntity> findAllByUserId(Long userId);

    void deleteByUserId(Long userId);
}

