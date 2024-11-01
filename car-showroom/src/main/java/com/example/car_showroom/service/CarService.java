package com.example.car_showroom.service;

import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.car.CreateCarRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CarService {
    ResponseEntity<GeneralResponseDTO> createNewCarInShowroom(String acceptedLanguage, String crn, CreateCarRequestDTO createCarRequestDTO);
    ResponseEntity<Object> getCarsWithFilters(String acceptedLanguage, Map<String, String> requestParams, int pageNumber, int pageLimit, String sortBy, String sortType);

}
