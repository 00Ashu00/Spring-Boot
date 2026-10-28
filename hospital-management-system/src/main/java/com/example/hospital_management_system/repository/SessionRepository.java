package com.example.hospital_management_system.repository;


import com.example.hospital_management_system.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    void deleteByUserId(Long userId);

    Optional<SessionEntity> findByToken(String token);

}
