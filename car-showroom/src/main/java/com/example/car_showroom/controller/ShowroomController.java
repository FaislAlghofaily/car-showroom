package com.example.car_showroom.controller;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.CreateNewShowroomRequestDTO;
import com.example.car_showroom.dto.showroom.ShowroomFiltersDTO;
import com.example.car_showroom.dto.showroom.UpdateShowroomRequestDTO;
import com.example.car_showroom.service.ShowroomService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * @param createNewShowroomRequestDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<GeneralResponseDTO> createNewShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, required = false, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE) final String acceptedLanguage,
                                                                @RequestBody @Valid CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        logger.info("Incoming request to create new showroom with CRN: " + createNewShowroomRequestDTO.getCrn());
        return showroomService.createNewShowroom(acceptedLanguage, createNewShowroomRequestDTO);
    }

    /**
     * this api is responsible for getting showrooms with dynamic filters
     * @param acceptedLanguage
     * @param pageNumber
     * @param pageLimit
     * @param sortBy
     * @param sortType
     * @param filters
     * @return
     */
    @GetMapping("/GetShowrooms")
    public ResponseEntity<Object> getItemsByRequestBody(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                        @RequestParam(value = ApplicationConstants.PAGE_NUMBER, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) final int pageNumber,
                                                        @RequestParam(value = ApplicationConstants.PAGE_LIMIT, defaultValue = ApplicationConstants.DEFAULT_PAGE_LIMIT, required = false) final int pageLimit,
                                                        @RequestParam(value = ApplicationConstants.SORT_BY, required = false) final String sortBy,
                                                        @RequestParam(value = ApplicationConstants.SORT_TYPE, defaultValue = ApplicationConstants.SORT_ASC, required = false) final String sortType,
                                                        @RequestParam(required = false) Map<String, String> filters) {
        return showroomService.getShowrooms(acceptedLanguage, filters, pageNumber, pageLimit, sortBy, sortType);
    }

    /**
     * this api is responsible for fetching a showroom using CRN
     * @param acceptedLanguage
     * @param crn
     * @return
     */
    @GetMapping("/{crn}")
    public ResponseEntity<ShowroomFiltersDTO> getShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                          @PathVariable String crn) {
        return showroomService.getShowroomWithCRN(acceptedLanguage, crn);
    }

    /**
     * this api is responsible for updating showroom's address, contactNumber, managerName
     * @param acceptedLanguage
     * @param crn
     * @param updateShowroomRequestDTO
     * @return
     */
    @PutMapping("/{crn}")
    public ResponseEntity<GeneralResponseDTO> updateShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                             @PathVariable String crn,
                                                             @RequestBody(required = false) UpdateShowroomRequestDTO updateShowroomRequestDTO) {
        return showroomService.updateShowroom(acceptedLanguage, crn, updateShowroomRequestDTO);
    }

    /**
     * this api is responsible for inactivating showroom
     * @param acceptedLanguage
     * @param crn
     * @return
     */
    @PutMapping("/inactivateShowroom/{crn}")
    public ResponseEntity<GeneralResponseDTO> updateShowroom(@RequestHeader(value = ApplicationConstants.HEADER_LANGUAGE, defaultValue = ApplicationConstants.DEFAULT_LANGUAGE, required = false) final String acceptedLanguage,
                                                             @PathVariable String crn) {
        return showroomService.inactivateShowroom(acceptedLanguage, crn);
    }
}
