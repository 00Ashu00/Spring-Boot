package com.learningspringboot.ashu.dto;

import com.learningspringboot.ashu.validations.ValidPassword;

public class PasswordDTO {

    @ValidPassword
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
