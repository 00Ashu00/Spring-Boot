package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.entity.Patient;
import com.example.hospital_management_system.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private PatientService patientService;
   public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id){
       return patientService.getPatientById(id);
    }
    @PostMapping
    public Patient addPatient(@RequestBody Patient patient){
       return patientService.addPatient(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id,@RequestBody Patient patient){
       return patientService.updatePatient(id,patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
