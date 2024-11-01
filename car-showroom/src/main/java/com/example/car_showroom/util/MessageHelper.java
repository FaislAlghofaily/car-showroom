package com.example.car_showroom.util;

import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.enums.StatusEnum;
import com.example.car_showroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

    @Autowired
    private MessageService messageService;

    public GeneralResponseDTO getSuccessResponse(String language, String message) {
        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
        generalResponseDTO.setMessage(messageService.getMessage(language, message));
        generalResponseDTO.setStatus(StatusEnum.SUCCESS.getStatus());
        return generalResponseDTO;
    }
}
