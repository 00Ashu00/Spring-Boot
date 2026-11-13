package com.example.hospital_management_system.service;

import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.exceptions.DoctorNotFoundException;
import com.example.hospital_management_system.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void testSaveDoctor() {

        Doctor doctor = new Doctor();
        doctor.setName("John");
        doctor.setSpecialization("Cardiology");
        doctor.setEmail("john@gmail.com");

        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor savedDoctor = doctorService.saveDoctor(doctor);

        assertThat(savedDoctor).isNotNull();
        assertThat(savedDoctor.getName()).isEqualTo("John");
        verify(doctorRepository).save(doctor);
    }


    @Test
    void testGetDoctorById_whenDoctorFound() {
        Long id = 1L;

        Doctor doctor = new Doctor();
        doctor.setName("John");
        doctor.setSpecialization("Cardiology");
        doctor.setEmail("john@gmail.com");

        when(doctorRepository.findById(id))
                .thenReturn(Optional.of(doctor));

        Doctor foundDoctor = doctorService.getDoctorById(id);

        assertThat(foundDoctor).isNotNull();
        assertThat(foundDoctor.getName()).isEqualTo("John");
        assertThat(foundDoctor.getSpecialization()).isEqualTo("Cardiology");
        assertThat(foundDoctor.getEmail()).isEqualTo("john@gmail.com");

        verify(doctorRepository).findById(id);
    }


    @Test
    void testGetDoctorById_whenDoctorNotFound() {
        Long id = 1L;

        when(doctorRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> doctorService.getDoctorById(id))
                .isInstanceOf(DoctorNotFoundException.class);

        verify(doctorRepository).findById(id);
    }

    @Test
    void testGetAllDoctors(){
        Doctor doctor1 = new Doctor();
        doctor1.setName("John");
        doctor1.setSpecialization("Cardiology");
        doctor1.setEmail("john@gmail.com");

        Doctor doctor2 = new Doctor();
        doctor2.setName("Knor");
        doctor2.setSpecialization("ENT");
        doctor2.setEmail("knor@gmail.com");

        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1,doctor2));

        List<Doctor> allDoctors = doctorService.getAllDoctors();

                assertThat(allDoctors).isNotNull();
                assertThat(allDoctors.size()).isEqualTo(2);
                assertThat(allDoctors.get(0).getName()).isEqualTo("John");
                assertThat(allDoctors.get(1).getName()).isEqualTo("Knor");
                verify(doctorRepository).findAll();

    }
    @Test
    void testGetAllDoctors_whenRepositoryThrowsException() {

        when(doctorRepository.findAll())
                .thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> doctorService.getAllDoctors())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");

        verify(doctorRepository).findAll();
    }
    @Test
    void testDoctorUpdate_whenDoctorFound(){
        Long id=1L;
        Doctor existingDoctor = new Doctor();
        existingDoctor.setName("John");
        existingDoctor.setSpecialization("Cardiology");
        existingDoctor.setEmail("john@gmail.com");

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Alex");
        updatedDoctor.setSpecialization("Neurology");
        updatedDoctor.setEmail("alex@gmail.com");

        when(doctorRepository.findById(id))
                .thenReturn(Optional.of(existingDoctor));

        when(doctorRepository.save(existingDoctor))
                .thenReturn(existingDoctor);

        Doctor doc = doctorService.updateDoctor(id, updatedDoctor);

        assertThat(doc.getName()).isEqualTo("Alex");
        assertThat(doc.getSpecialization()).isEqualTo("Neurology");
        assertThat(doc.getEmail()).isEqualTo("alex@gmail.com");

        verify(doctorRepository).findById(id);

        ArgumentCaptor<Doctor> doctorCaptor =
                ArgumentCaptor.forClass(Doctor.class);

        verify(doctorRepository).save(doctorCaptor.capture());

        Doctor capturedDoctor = doctorCaptor.getValue();

        assertThat(capturedDoctor.getName()).isEqualTo("Alex");
        assertThat(capturedDoctor.getSpecialization()).isEqualTo("Neurology");
        assertThat(capturedDoctor.getEmail()).isEqualTo("alex@gmail.com");


    }
    @Test
    void testDoctorUpdate_whenDoctorNotFound() {
        Long id = 1L;

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Alex");
        updatedDoctor.setSpecialization("Neurology");
        updatedDoctor.setEmail("alex@gmail.com");

        when(doctorRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> doctorService.updateDoctor(id, updatedDoctor))
                .isInstanceOf(DoctorNotFoundException.class);

        verify(doctorRepository).findById(id);
        verify(doctorRepository, never()).save(any(Doctor.class));
    }



    @Test
    void testDoctorDelete_whenDoctorFound(){

        Long id=1L;
            Doctor doctor=new Doctor();

            doctor.setName("John");
            doctor.setSpecialization("Cardiology");
            doctor.setEmail("john@gmail.com");

            when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
            doctorService.deleteDoctor(id);

            verify(doctorRepository).findById(id);
            verify(doctorRepository).deleteById(id);

        }
    @Test
    void testDoctorDelete_whenDoctorNotFound() {
        Long id = 1L;

        when(doctorRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> doctorService.deleteDoctor(id))
                .isInstanceOf(DoctorNotFoundException.class);

        verify(doctorRepository).findById(id);
        verify(doctorRepository, never()).deleteById(id);
    }


    @Test
    void testRepositoryCalledAtLeastTwice() {

        doctorService.getAllDoctors();
        doctorService.getAllDoctors();

        verify(doctorRepository, atLeast(2)).findAll();
    }


}