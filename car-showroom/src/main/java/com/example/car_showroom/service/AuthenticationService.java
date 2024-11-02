package com.example.car_showroom.service;

import com.example.car_showroom.dto.user.CreateUserDTO;
import com.example.car_showroom.dto.user.LoginResponseDTO;
import com.example.car_showroom.dto.user.LoginUserDTO;
import com.example.car_showroom.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<User> signup(CreateUserDTO input);
    ResponseEntity<LoginResponseDTO> login(String acceptedLanguage, LoginUserDTO loginUserDTO);
}
