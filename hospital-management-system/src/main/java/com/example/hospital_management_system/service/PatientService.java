package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.Patient;
import com.example.hospital_management_system.repository.PatientRepository;
import org.springframework.stereotype.Service;
import com.example.hospital_management_system.exceptions.PatientNotFoundException;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }
    public Patient getPatientById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(()->
                        new PatientNotFoundException("Patient not found :"+id));
    }
    public Patient addPatient(Patient patient){
        return patientRepository.save(patient);
    }
    public Patient updatePatient(Long id,Patient patient){
        Patient updatedPatient =getPatientById(id);
        updatedPatient.setName(patient.getName());
        updatedPatient.setEmail(patient.getEmail());
        updatedPatient.setPhone(patient.getPhone());
        updatedPatient.setAge(patient.getAge());
        return patientRepository.save(updatedPatient);
    }
    public void deletePatient(Long id){
        getPatientById(id);
        patientRepository.deleteById(id);
    }
}
