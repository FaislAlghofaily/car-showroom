package com.example.car_showroom.service;

import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.CreateNewShowroomRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ShowroomService {
    ResponseEntity<GeneralResponseDTO> createNewShowroom(String acceptedLanguage, CreateNewShowroomRequestDTO createNewShowroomRequestDTO);

    ResponseEntity<Object> getShowrooms(String acceptedLanguage, Map<String, String> requestParams, int pageNumber, int pageLimit, String sortBy, String sortType);
}
