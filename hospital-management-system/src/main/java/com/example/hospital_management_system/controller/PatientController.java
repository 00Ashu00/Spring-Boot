package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.entity.Patient;
import com.example.hospital_management_system.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private PatientService patientService;
   public PatientController(PatientService patientService) {

       this.patientService = patientService;
    }
    @GetMapping
    public List<Patient> getAllPatients(){

       logger.info("Getting all patients");
       return patientService.getAllPatients();
    }
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id){

       logger.info("Getting patient by id {}", id);
       return patientService.getPatientById(id);
    }
    @PostMapping
    public Patient addPatient(@RequestBody Patient patient){

       return patientService.addPatient(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id,@RequestBody Patient patient){
       logger.info("Updating patient {}", patient);
       return patientService.updatePatient(id,patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
       logger.info("Deleting patient {}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
