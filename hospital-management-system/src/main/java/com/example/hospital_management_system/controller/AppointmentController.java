
package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.dto.AppointmentRequest;
import com.example.hospital_management_system.entity.Appointment;
import com.example.hospital_management_system.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public Page<Appointment> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return appointmentService.getAllAppointments(pageable);
    }

    @GetMapping("/{id}")
    public Appointment findByAppointmentId(@PathVariable Long id) {
        return appointmentService.findByAppointmentId(id);
    }

    @PostMapping
    public ResponseEntity<Appointment> addAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        Appointment appointment = appointmentService.addAppointment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request) {

        return appointmentService.updateAppointment(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.noContent().build();
    }
}

