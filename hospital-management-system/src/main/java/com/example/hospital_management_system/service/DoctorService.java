package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.exceptions.DoctorNotFoundException;
import com.example.hospital_management_system.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public Doctor getDoctorById(Long id) {
        logger.info("getDoctorById");
        return doctorRepository.findById(id)
                .orElseThrow(()->
                        new DoctorNotFoundException("Doctor not found with id: " + id));
    }
    public List<Doctor> getAllDoctors() {
        logger.info("getAllDoctors");
        return doctorRepository.findAll();
    }
    public Doctor saveDoctor(Doctor doctor) {
        logger.info("saveDoctor");
        return doctorRepository.save(doctor);
    }
    public Doctor updateDoctor(Long id, Doctor doctor) {
        logger.info("updateDoctor");
        Doctor existingDoctor = getDoctorById(id);

        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setEmail(doctor.getEmail());

        return doctorRepository.save(existingDoctor);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDoctor(Long id) {
        logger.info("deleteDoctor");
            getDoctorById(id);
        doctorRepository.deleteById(id);
    }

}
