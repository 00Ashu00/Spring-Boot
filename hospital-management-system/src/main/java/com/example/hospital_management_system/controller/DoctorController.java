package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private static  final Logger logger= LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;


    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors(){
        logger.info("getAllDoctors");
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public Doctor saveDoctor(@RequestBody Doctor doctor){
        logger.info("Doctor save request");
        return doctorService.saveDoctor(doctor);
    }
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id){

        logger.info("getDoctorById");
        return doctorService.getDoctorById(id);
    }
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id,@RequestBody Doctor doctor){
        logger.info("updateDoctor");
        return doctorService.updateDoctor(id,doctor);
    }
    @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        logger.info("deleteDoctor");
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}
