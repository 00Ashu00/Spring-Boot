
package com.example.hospital_management_system.service;

import com.example.hospital_management_system.controller.AppointmentController;
import com.example.hospital_management_system.dto.AppointmentRequest;
import com.example.hospital_management_system.entity.Appointment;
import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.entity.Patient;
import com.example.hospital_management_system.exceptions.AppointmentNotFoundException;
import com.example.hospital_management_system.exceptions.DoctorNotFoundException;
import com.example.hospital_management_system.exceptions.PatientNotFoundException;
import com.example.hospital_management_system.repository.AppointmentRepository;
import com.example.hospital_management_system.repository.DoctorRepository;
import com.example.hospital_management_system.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public Page<Appointment> getAllAppointments(Pageable pageable) {
        logger.info("fetching all appointments");
        return appointmentRepository.findAll(pageable);
    }

    public Appointment findByAppointmentId(long id) {
        logger.info("fetching appointment with id={}", id);
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with id: " + id));
    }

    public Appointment addAppointment(AppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + request.getDoctorId()));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + request.getPatientId()));

        Appointment appointment = new Appointment();

        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, AppointmentRequest request) {
logger.info("updating appointment with id={}", id);
        Appointment updatedAppointment = findByAppointmentId(id);

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + request.getDoctorId()));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + request.getPatientId()));

        updatedAppointment.setReason(request.getReason());
        updatedAppointment.setAppointmentTime(request.getAppointmentTime());
        updatedAppointment.setStatus(request.getStatus());
        updatedAppointment.setDoctor(doctor);
        updatedAppointment.setPatient(patient);

        return appointmentRepository.save(updatedAppointment);
    }

    public void deleteAppointment(Long id) {
        logger.info("deleting appointment with id={}", id);
        findByAppointmentId(id);
        appointmentRepository.deleteById(id);
    }
}

