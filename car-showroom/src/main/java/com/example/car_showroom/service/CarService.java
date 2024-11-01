package com.example.car_showroom.service;

import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.car.CreateCarRequestDTO;
import org.springframework.http.ResponseEntity;

public interface CarService {
    ResponseEntity<GeneralResponseDTO> createNewCarInShowroom(String acceptedLanguage, String crn, CreateCarRequestDTO createCarRequestDTO);
}
