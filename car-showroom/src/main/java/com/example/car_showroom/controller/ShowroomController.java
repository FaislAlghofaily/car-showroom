package com.example.car_showroom.controller;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.GeneralShowroomDTO;
import com.example.car_showroom.service.ShowroomService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/showroom")
public class ShowroomController {
    private static final Logger logger = LoggerFactory.getLogger(ShowroomController.class);

    @Autowired
    private ShowroomService showroomService;

    /**
     * this api is for creating a new showroom
     *
     * @param acceptedLanguage
     * @param generalShowroomDTO
     * @return
     */
    @PostMapping("/create")
    public GeneralResponseDTO createNewShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, required = false, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE) final String acceptedLanguage,
                                                @RequestBody @Valid GeneralShowroomDTO generalShowroomDTO) {
        logger.info("Incoming request to create new showroom with CRN: " + generalShowroomDTO.getCrn());
        return showroomService.createNewShowroom(acceptedLanguage, generalShowroomDTO);
    }

}
