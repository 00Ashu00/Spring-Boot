package com.learningspringboot.ashu.dto;

import com.learningspringboot.ashu.validations.PrimeNumber;

public class PrimeNumberDTO {

    @PrimeNumber
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
