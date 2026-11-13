package com.example.hospital_management_system.repository;

import com.example.hospital_management_system.config.PostgresTestContainerConfig;
import com.example.hospital_management_system.entity.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Import(PostgresTestContainerConfig.class)
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;


    @Test
    void testDoctorSave(){
        Doctor doctor = new Doctor();
        doctor.setName("John");
        doctor.setSpecialization("Cardiology");
        doctor.setEmail("john@gmail.com");

        Doctor savedDoctor = doctorRepository.save(doctor);

        assertThat(savedDoctor.getId()).isNotNull();
        assertThat(savedDoctor.getName()).isEqualTo(doctor.getName());
        assertThat(savedDoctor.getSpecialization()).isEqualTo(doctor.getSpecialization());
        assertThat(savedDoctor.getEmail()).isEqualTo(doctor.getEmail());
    }
    @Test
    void testDoctorById(){
        Doctor doctor = new Doctor();

        doctor.setName("John");
        doctor.setSpecialization("Cardiology");
        doctor.setEmail("john@gmail.com");
        Doctor savedDoctor = doctorRepository.save(doctor);

        Doctor foundDoctor = doctorRepository.findById(savedDoctor.getId()).get();
        assertThat(foundDoctor).isNotNull();
        assertThat(foundDoctor.getName()).isEqualTo(doctor.getName());
        assertThat(foundDoctor.getSpecialization()).isEqualTo(doctor.getSpecialization());
        assertThat(foundDoctor.getEmail()).isEqualTo(doctor.getEmail());

    }

    @Test
    void testDoctorFindById(){
        Doctor doctor1 = new Doctor();
        doctor1.setName("John");
        doctor1.setSpecialization("Cardiology");
        doctor1.setEmail("john@gmail.com");

        Doctor doctor2 = new Doctor();
        doctor2.setName("Knor");
        doctor2.setSpecialization("ENT");
        doctor2.setEmail("knor@gmail.com");

        Doctor savedDoctor1 = doctorRepository.save(doctor1);
        Doctor savedDoctor2 = doctorRepository.save(doctor2);

        List<Doctor> getall=doctorRepository.findAll();
        assertThat(getall).isNotNull();
        assertThat(getall.size()).isEqualTo(2);
        assertThat(savedDoctor1.getId()).isNotNull();
        assertThat(savedDoctor2.getId()).isNotNull();
        assertThat(savedDoctor1.getName()).isEqualTo(doctor1.getName());
        assertThat(savedDoctor1.getSpecialization()).isEqualTo(doctor1.getSpecialization());
        assertThat(savedDoctor1.getEmail()).isEqualTo(doctor1.getEmail());
        assertThat(savedDoctor2.getName()).isEqualTo(doctor2.getName());
        assertThat(savedDoctor2.getSpecialization()).isEqualTo(doctor2.getSpecialization());
        assertThat(savedDoctor2.getEmail()).isEqualTo(doctor2.getEmail());
    }

    @Test
    void testDeleteDoctorById() {
        Doctor doctor = new Doctor();
        doctor.setName("John");
        doctor.setSpecialization("Cardiology");
        doctor.setEmail("john@gmail.com");

        Doctor savedDoctor = doctorRepository.save(doctor);

        doctorRepository.deleteById(savedDoctor.getId());

        assertThat(doctorRepository.findById(savedDoctor.getId())).isEmpty();
    }
}