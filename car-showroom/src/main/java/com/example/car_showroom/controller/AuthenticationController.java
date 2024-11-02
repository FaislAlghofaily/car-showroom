package com.example.car_showroom.controller;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.dto.user.CreateUserDTO;
import com.example.car_showroom.dto.user.LoginResponseDTO;
import com.example.car_showroom.dto.user.LoginUserDTO;
import com.example.car_showroom.entity.User;
import com.example.car_showroom.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * this method is responsible for creating new users
     * @param acceptedLanguage
     * @param registerUserDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                         @RequestBody CreateUserDTO registerUserDto) {
        return authenticationService.signup(registerUserDto);
    }

    /**
     * this method authenticates and returns JWT token
     * @param acceptedLanguage
     * @param loginUserDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                         @RequestBody LoginUserDTO loginUserDto) {
        return authenticationService.login(acceptedLanguage, loginUserDto);
    }
}