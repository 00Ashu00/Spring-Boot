package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.dto.AppointmentRequest;
import com.example.hospital_management_system.entity.Appointment;
import com.example.hospital_management_system.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
    @GetMapping("/{id}")
    public Appointment  findByAppointmentId(@PathVariable Long id) {
        return appointmentService. findByAppointmentId(id);
    }
    @PostMapping
    public Appointment addAppointment(@RequestBody AppointmentRequest request) {
        return appointmentService.addAppointment(request);
    }
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable long id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
    }
}
