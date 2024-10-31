package com.example.car_showroom.service;

import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.GeneralShowroomDTO;

public interface ShowroomService {
    GeneralResponseDTO createNewShowroom(String acceptedLanguage, GeneralShowroomDTO generalShowroomDTO);
}
