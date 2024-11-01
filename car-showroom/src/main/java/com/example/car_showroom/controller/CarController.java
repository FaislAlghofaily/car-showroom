package com.example.car_showroom.controller;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.car.CreateCarRequestDTO;
import com.example.car_showroom.service.CarService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @PostMapping("/{crn}")
    public ResponseEntity<GeneralResponseDTO> getShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                          @PathVariable String crn,
                                                          @RequestBody @Valid CreateCarRequestDTO createCarRequestDTO) {
        return carService.createNewCarInShowroom(acceptedLanguage, crn, createCarRequestDTO);
    }

}
