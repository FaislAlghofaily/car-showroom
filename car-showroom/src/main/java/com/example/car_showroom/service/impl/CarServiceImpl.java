package com.example.car_showroom.service.impl;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.car.CreateCarRequestDTO;
import com.example.car_showroom.entity.Car;
import com.example.car_showroom.entity.Showroom;
import com.example.car_showroom.enums.CarStatusEnum;
import com.example.car_showroom.exception.CustomException;
import com.example.car_showroom.repository.CarRepository;
import com.example.car_showroom.repository.ShowroomRepository;
import com.example.car_showroom.service.CarService;
import com.example.car_showroom.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ShowroomRepository showroomRepository;
    @Autowired
    private MessageHelper messageHelper;


    @Override
    public ResponseEntity<GeneralResponseDTO> createNewCarInShowroom(String acceptedLanguage, String crn, CreateCarRequestDTO createCarRequestDTO) {
        Showroom showroom = Optional.ofNullable(showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE)).orElseThrow(() ->
                new CustomException(acceptedLanguage, "Showroom not found"));
        createAndSaveNewCar(createCarRequestDTO, showroom);
        return new ResponseEntity<>(messageHelper.getSuccessResponse(acceptedLanguage, "message"), HttpStatus.OK);
    }

    private Car createAndSaveNewCar(CreateCarRequestDTO createCarRequestDTO, Showroom showroom) {
        Car car = new Car();
        BeanUtils.copyProperties(createCarRequestDTO, car);
        car.setShowroom(showroom);
        car.setStatus(CarStatusEnum.AVAILABLE.getStatus());
        return carRepository.save(car);
    }
}
