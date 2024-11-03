package com.example.car_showroom.dto.user;

import com.example.car_showroom.constant.ErrorMessageConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginUserDTO {
    @NotBlank(message = ErrorMessageConstant.MUST_NOT_BE_NULL)
    @Size(min = 1, max = 100)
    private String email;
    @NotBlank(message = ErrorMessageConstant.MUST_NOT_BE_NULL)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
