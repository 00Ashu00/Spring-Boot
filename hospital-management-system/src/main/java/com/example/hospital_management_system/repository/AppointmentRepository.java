package com.example.hospital_management_system.repository;

import com.example.hospital_management_system.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
