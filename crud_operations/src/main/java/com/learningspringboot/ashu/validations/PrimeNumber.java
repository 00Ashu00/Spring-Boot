package com.learningspringboot.ashu.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PrimeNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimeNumber {

    String message() default "Number must be prime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
