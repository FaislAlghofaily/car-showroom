package com.example.car_showroom.service.impl;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.constant.ErrorMessageConstant;
import com.example.car_showroom.constant.MessageConstant;
import com.example.car_showroom.controller.ShowroomController;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.GeneralShowroomDTO;
import com.example.car_showroom.entity.Showroom;
import com.example.car_showroom.enums.StatusEnum;
import com.example.car_showroom.exception.CustomException;
import com.example.car_showroom.repository.ShowroomRepository;
import com.example.car_showroom.service.MessageService;
import com.example.car_showroom.service.ShowroomService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShowroomServiceImpl implements ShowroomService {
    private static final Logger logger = LoggerFactory.getLogger(ShowroomController.class);
    @Autowired
    private ShowroomRepository showroomRepository;
    @Autowired
    private MessageService messageService;

    @Override
    public ResponseEntity<GeneralResponseDTO> createNewShowroom(String acceptedLanguage, GeneralShowroomDTO generalShowroomDTO) {
        validateCreateShowroomRequest(acceptedLanguage, generalShowroomDTO);
        createAndSaveShowroom(generalShowroomDTO);
        return new ResponseEntity<>(getCreateShowroomSuccessResponse(acceptedLanguage), HttpStatus.OK);
    }

    private Showroom createAndSaveShowroom(GeneralShowroomDTO generalShowroomDTO) {
        Showroom showroom = new Showroom();
        BeanUtils.copyProperties(generalShowroomDTO, showroom);
        showroom.setCreatedDate(new Date());
        showroom.setUpdatedDate(new Date());
        showroom.setCommercialRegistrationNumber(Long.valueOf(generalShowroomDTO.getCrn()));
        if (!StringUtils.isBlank(generalShowroomDTO.getContactNumber())) {
            showroom.setContactNumber(Long.valueOf(generalShowroomDTO.getContactNumber()));
        }
        return showroomRepository.save(showroom);
    }

    private void validateCreateShowroomRequest(String acceptedLanguage, GeneralShowroomDTO generalShowroomDTO) {
        if (!generalShowroomDTO.getCrn().matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + generalShowroomDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(generalShowroomDTO.getCrn()))) {
            logger.error("Error while creating showroom duplicate CRN:" + generalShowroomDTO.getCrn());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!StringUtils.isBlank(generalShowroomDTO.getContactNumber()) && !generalShowroomDTO.getContactNumber().matches(ApplicationConstants.MAX_15_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + generalShowroomDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
    }

    private GeneralResponseDTO getCreateShowroomSuccessResponse(String language) {
        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
        generalResponseDTO.setMessage(messageService.getMessage(language, MessageConstant.CREATE_SHOWROOM_SUCCESS));
        generalResponseDTO.setStatus(StatusEnum.SUCCESS.getStatus());
        return generalResponseDTO;
    }
}
