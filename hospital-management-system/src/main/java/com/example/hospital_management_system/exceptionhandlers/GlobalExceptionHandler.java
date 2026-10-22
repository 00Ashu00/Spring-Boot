package com.example.hospital_management_system.exceptionhandlers;

import com.example.hospital_management_system.exceptions.AppointmentNotFoundException;
import com.example.hospital_management_system.exceptions.DoctorNotFoundException;
import com.example.hospital_management_system.exceptions.PatientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> handleDoctorNotFound(DoctorNotFoundException ex) {
        log.error(ex.getMessage());
return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFound(PatientNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<String> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<String> (ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(

            MethodArgumentNotValidException ex) {

log.error(ex.getMessage());
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        log.error("Database integrity violation", ex);

        Map<String, String> error = Map.of(
                "error", "Database integrity constraint violated. Check the application logs for details."
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


}
