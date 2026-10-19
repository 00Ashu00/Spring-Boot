package com.example.hospital_management_system.controller;

import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }
    @PostMapping
    public Doctor saveDoctor(@RequestBody Doctor doctor){
        return doctorService.saveDoctor(doctor);
    }
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id){
        return doctorService.getDoctorById(id);
    }
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id,@RequestBody Doctor doctor){
        return doctorService.updateDoctor(id,doctor);
    }
    @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}
