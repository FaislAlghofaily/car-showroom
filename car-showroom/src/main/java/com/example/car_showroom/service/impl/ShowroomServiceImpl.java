package com.example.car_showroom.service.impl;

import com.example.car_showroom.constant.ErrorMessageConstant;
import com.example.car_showroom.constant.MessageConstant;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.GeneralShowroomDTO;
import com.example.car_showroom.entity.Showroom;
import com.example.car_showroom.enums.StatusEnum;
import com.example.car_showroom.exception.CustomException;
import com.example.car_showroom.repository.ShowroomRepository;
import com.example.car_showroom.service.MessageService;
import com.example.car_showroom.service.ShowroomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShowroomServiceImpl implements ShowroomService {

    @Autowired
    private ShowroomRepository showroomRepository;
    @Autowired
    private MessageService messageService;

    @Override
    public GeneralResponseDTO createNewShowroom(String acceptedLanguage, GeneralShowroomDTO generalShowroomDTO) {
        validateCreateShowroomRequest(acceptedLanguage, generalShowroomDTO);
        createAndSaveShowroom(generalShowroomDTO);
        return getCreateShowroomSuccessResponse(acceptedLanguage);
    }

    private Showroom createAndSaveShowroom(GeneralShowroomDTO generalShowroomDTO) {
        Showroom showroom = new Showroom();
        BeanUtils.copyProperties(generalShowroomDTO, showroom);
        showroom.setCreatedDate(new Date());
        showroom.setUpdatedDate(new Date());
        return showroomRepository.save(showroom);
    }
    private void validateCreateShowroomRequest(String acceptedLanguage, GeneralShowroomDTO generalShowroomDTO){
        if(showroomRepository.existsByCommercialRegistrationNumber(generalShowroomDTO.getCrn())){
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
    }
    private GeneralResponseDTO getCreateShowroomSuccessResponse(String language){
        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
        generalResponseDTO.setMessage(messageService.getMessage(language, MessageConstant.CREATE_SHOWROOM_SUCCESS));
        generalResponseDTO.setStatus(StatusEnum.SUCCESS.getStatus());
        return generalResponseDTO;
    }
}
