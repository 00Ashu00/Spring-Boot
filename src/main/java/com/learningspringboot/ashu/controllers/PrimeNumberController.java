package com.learningspringboot.ashu.controllers;

import com.learningspringboot.ashu.dto.PasswordDTO;
import com.learningspringboot.ashu.dto.PrimeNumberDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validate")
public class PrimeNumberController {

    @PostMapping("/prime")
    public String validatePrime(
            @Valid @RequestBody PrimeNumberDTO request) {
        return request.getNumber() + " is a prime number";
    }
    @PostMapping("/password")
    public String validatePassword(
            @Valid @RequestBody PasswordDTO request) {
        return "Password is valid";
    }
}
