package com.example.car_showroom.service;

import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.GeneralShowroomDTO;
import org.springframework.http.ResponseEntity;

public interface ShowroomService {
    ResponseEntity<GeneralResponseDTO> createNewShowroom(String acceptedLanguage, GeneralShowroomDTO generalShowroomDTO);
}
