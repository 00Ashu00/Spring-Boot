package com.example.hospital_management_system.dto;

import java.time.LocalDateTime;

public class AppointmentRequest {
    String reason;
    String status;
    LocalDateTime appointmentTime;
    Long doctorId;
    Long patientId;
    public Long getDoctorId() {
        return doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }
    public String getReason() {
        return reason;
    }
    public String getStatus() {
        return status;
    }
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
}
