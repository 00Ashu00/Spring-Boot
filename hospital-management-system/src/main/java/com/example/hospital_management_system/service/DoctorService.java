package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.exceptions.DoctorNotFoundException;
import com.example.hospital_management_system.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(()->
                        new DoctorNotFoundException("Doctor not found with id: " + id));
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = getDoctorById(id);

        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setEmail(doctor.getEmail());

        return doctorRepository.save(existingDoctor);
    }
    public void deleteDoctor(Long id) {
            getDoctorById(id);
        doctorRepository.deleteById(id);
    }

}
