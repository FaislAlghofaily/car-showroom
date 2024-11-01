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

import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    /**
     * this api is responsible for creating a new car in a specific showroom
     *
     * @param acceptedLanguage
     * @param crn
     * @param createCarRequestDTO
     * @return
     */
    @PostMapping("/{crn}")
    public ResponseEntity<GeneralResponseDTO> getShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                          @PathVariable String crn,
                                                          @RequestBody @Valid CreateCarRequestDTO createCarRequestDTO) {
        logger.info("new request to add car for showroom :" + crn);
        return carService.createNewCarInShowroom(acceptedLanguage, crn, createCarRequestDTO);
    }

    /**
     * this api is responsible for retrieving cars based on dynamic input filters
     *
     * @param acceptedLanguage
     * @param pageNumber
     * @param pageLimit
     * @param sortBy
     * @param sortType
     * @param filters
     * @return
     */
    @GetMapping("/GetCars")
    public ResponseEntity<Object> getItemsByRequestBody(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                        @RequestParam(value = ApplicationConstants.PAGE_NUMBER, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) final int pageNumber,
                                                        @RequestParam(value = ApplicationConstants.PAGE_LIMIT, defaultValue = ApplicationConstants.DEFAULT_PAGE_LIMIT, required = false) final int pageLimit,
                                                        @RequestParam(value = ApplicationConstants.SORT_BY, required = false) final String sortBy,
                                                        @RequestParam(value = ApplicationConstants.SORT_TYPE, defaultValue = ApplicationConstants.SORT_ASC, required = false) final String sortType,
                                                        @RequestParam(required = false) Map<String, String> filters) {
        return carService.getCarsWithFilters(acceptedLanguage, filters, pageNumber, pageLimit, sortBy, sortType);
    }

}
