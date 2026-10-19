package com.example.hospital_management_system.service;


import com.example.hospital_management_system.entity.Appointment;
import com.example.hospital_management_system.entity.Doctor;
import com.example.hospital_management_system.entity.Patient;
import com.example.hospital_management_system.exceptions.DoctorNotFoundException;
import com.example.hospital_management_system.exceptions.PatientNotFoundException;
import com.example.hospital_management_system.repository.AppointmentRepository;
import com.example.hospital_management_system.repository.DoctorRepository;
import com.example.hospital_management_system.repository.PatientRepository;
import org.springframework.stereotype.Service;
import com.example.hospital_management_system.exceptions.AppointmentNotFoundException;
import com.example.hospital_management_system.dto.AppointmentRequest;


import java.util.List;

@Service
public class AppointmentService {
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
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();

    }
    public Appointment findByAppointmentId(long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(()->
                     new   AppointmentNotFoundException("Appointment not found with id:"+id));
    }

    public Appointment addAppointment(AppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with id: " + request.getDoctorId()
                ));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: " + request.getPatientId()
                ));

        Appointment appointment = new Appointment();

        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id,Appointment appointment) {
            Appointment updatedAppointment = findByAppointmentId(id);
            updatedAppointment.setReason(appointment.getReason());
        updatedAppointment.setAppointmentTime(appointment.getAppointmentTime());
        updatedAppointment.setStatus(appointment.getStatus());
        return appointmentRepository.save(updatedAppointment);
    }
    public void deleteAppointment(Long id) {
        findByAppointmentId(id);
        appointmentRepository.deleteById(id);
    }
}
