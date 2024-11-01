package com.example.car_showroom.service.impl;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.constant.ErrorMessageConstant;
import com.example.car_showroom.constant.MessageConstant;
import com.example.car_showroom.controller.ShowroomController;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.showroom.ShowroomListResponseDTO;
import com.example.car_showroom.dto.showroom.CreateNewShowroomRequestDTO;
import com.example.car_showroom.dto.showroom.ShowroomFiltersDTO;
import com.example.car_showroom.dto.showroom.UpdateShowroomRequestDTO;
import com.example.car_showroom.entity.Showroom;
import com.example.car_showroom.enums.StatusEnum;
import com.example.car_showroom.exception.CustomException;
import com.example.car_showroom.repository.ShowroomRepository;
import com.example.car_showroom.service.MessageService;
import com.example.car_showroom.service.ShowroomService;
import com.example.car_showroom.util.CommonUtils;
import com.example.car_showroom.util.PageableResponseConverter;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class ShowroomServiceImpl implements ShowroomService {
    private static final Logger logger = LoggerFactory.getLogger(ShowroomController.class);
    @Autowired
    private ShowroomRepository showroomRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PageableResponseConverter pageableResponseConverter;

    @Override
    public ResponseEntity<GeneralResponseDTO> createNewShowroom(String acceptedLanguage, CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        validateCreateShowroomRequest(acceptedLanguage, createNewShowroomRequestDTO);
        createAndSaveShowroom(createNewShowroomRequestDTO);
        return new ResponseEntity<>(getSuccessResponse(acceptedLanguage, MessageConstant.CREATE_SHOWROOM_SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getShowrooms(String acceptedLanguage, Map<String, String> requestParams, int pageNumber, int pageLimit, String sortBy, String sortType) {
        Page<Showroom> showrooms = getItemsWithFilters(PageRequest.of((pageNumber - 1), pageLimit), requestParams, sortBy, sortType);
        Object response = null;
        if (Optional.ofNullable(showrooms).isPresent() && !CollectionUtils.isEmpty(showrooms.getContent())) {
            response = getShowroomResponse(showrooms.getContent());
        }
        return pageableResponseConverter.getPageableResponse(response, showrooms, pageLimit, pageNumber);

    }

    @Override
    public ResponseEntity<ShowroomFiltersDTO> getShowroomWithCRN(String acceptedLanguage, String crn) {
        ShowroomFiltersDTO showroomFiltersDTO = new ShowroomFiltersDTO();
        Showroom showroom = Optional.ofNullable(showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE)).orElseThrow(() ->
                new CustomException(ErrorMessageConstant.NO_SHOWROOM_FOUND));
        showroomFiltersDTO.setCrn(crn);
        showroomFiltersDTO.setName(showroom.getName());
        showroomFiltersDTO.setAddress(showroom.getAddress());
        showroomFiltersDTO.setManagerName(showroom.getManagerName());
        showroomFiltersDTO.setContactNumber(String.valueOf(showroom.getContactNumber()));

        return new ResponseEntity(showroomFiltersDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponseDTO> updateShowroom(String acceptedLanguage, String crn, UpdateShowroomRequestDTO updateShowroomRequestDTO) {
        validateUpdateRequest(acceptedLanguage, updateShowroomRequestDTO, crn);
        Showroom showroom = showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE);
        if (!StringUtils.isBlank(updateShowroomRequestDTO.getContactNumber())) {
            showroom.setContactNumber(Long.valueOf(updateShowroomRequestDTO.getContactNumber()));
        }
        if (!StringUtils.isBlank(updateShowroomRequestDTO.getManagerName())) {
            showroom.setManagerName(updateShowroomRequestDTO.getManagerName());
        }
        if (!StringUtils.isBlank(updateShowroomRequestDTO.getAddress())) {
            showroom.setAddress(updateShowroomRequestDTO.getAddress());
        }
        showroomRepository.save(showroom);
        return new ResponseEntity<>(getSuccessResponse(acceptedLanguage, MessageConstant.CREATE_SHOWROOM_SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponseDTO> inactivateShowroom(String acceptedLanguage, String crn) {
        if (!crn.matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(crn))) {
            logger.error("Error while creating showroom duplicate CRN:" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        Showroom showroom = showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE);
        showroom.setStatus(ApplicationConstants.INACTIVE);
        showroomRepository.save(showroom);
        return new ResponseEntity<>(getSuccessResponse(acceptedLanguage, MessageConstant.CREATE_SHOWROOM_SUCCESS), HttpStatus.OK);
    }

    private Showroom createAndSaveShowroom(CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        Showroom showroom = new Showroom();
        BeanUtils.copyProperties(createNewShowroomRequestDTO, showroom);
        showroom.setCreatedDate(new Date());
        showroom.setUpdatedDate(new Date());
        showroom.setCommercialRegistrationNumber(Long.valueOf(createNewShowroomRequestDTO.getCrn()));
        if (!StringUtils.isBlank(createNewShowroomRequestDTO.getContactNumber())) {
            showroom.setContactNumber(Long.valueOf(createNewShowroomRequestDTO.getContactNumber()));
        }
        return showroomRepository.save(showroom);
    }

    private void validateCreateShowroomRequest(String acceptedLanguage, CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        if (!createNewShowroomRequestDTO.getCrn().matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + createNewShowroomRequestDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(createNewShowroomRequestDTO.getCrn()))) {
            logger.error("Error while creating showroom duplicate CRN:" + createNewShowroomRequestDTO.getCrn());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!StringUtils.isBlank(createNewShowroomRequestDTO.getContactNumber()) && !createNewShowroomRequestDTO.getContactNumber().matches(ApplicationConstants.MAX_15_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + createNewShowroomRequestDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
    }

    private GeneralResponseDTO getSuccessResponse(String language, String message) {
        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
        generalResponseDTO.setMessage(messageService.getMessage(language, message));
        generalResponseDTO.setStatus(StatusEnum.SUCCESS.getStatus());
        return generalResponseDTO;
    }

    private Page<Showroom> getItemsWithFilters(PageRequest pageRequest, Map<String, String> requestParams, String sortBy, String sortType) {
        return showroomRepository.findAll((Specification<Showroom>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_NAME))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_NAME), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_NAME) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_CRN))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_CRN).as(String.class), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_CRN) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_CONTACT_NUMBER))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_CONTACT_NUMBER).as(String.class), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_CONTACT_NUMBER) + ApplicationConstants.STRING_PERCENTAGE));
            }
            predicates.add(criteriaBuilder.equal(root.get(ApplicationConstants.ACTIVE), ApplicationConstants.ACTIVE));

            query.distinct(true);
            query.multiselect(root.get(ApplicationConstants.FILTER_NAME), root.get(ApplicationConstants.FILTER_CRN), root.get(ApplicationConstants.FILTER_CONTACT_NUMBER));
            if (StringUtils.isBlank(sortBy)) {
                if (sortType.equalsIgnoreCase(ApplicationConstants.SORT_ASC)) {
                    query.orderBy(criteriaBuilder.asc(root.get("id")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("id")));
                }
            } else {
                if (sortType.equalsIgnoreCase(ApplicationConstants.SORT_ASC)) {
                    query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageRequest);

    }

    private Object getShowroomResponse(List<Showroom> showrooms) {
        List<ShowroomListResponseDTO> showroomsListDTO = new ArrayList<>();
        showrooms.forEach(showroom -> {
            ShowroomListResponseDTO itemFilterDTO = CommonUtils.createObjectMapper().convertValue(showroom, ShowroomListResponseDTO.class);
            showroomsListDTO.add(itemFilterDTO);
        });
        return showroomsListDTO;
    }

    private void validateUpdateRequest(String acceptedLanguage, UpdateShowroomRequestDTO updateShowroomRequestDTO, String crn) {
        if (!crn.matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(crn))) {
            logger.error("Error while creating showroom duplicate CRN:" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!StringUtils.isBlank(updateShowroomRequestDTO.getContactNumber()) && !updateShowroomRequestDTO.getContactNumber().matches(ApplicationConstants.MAX_15_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + updateShowroomRequestDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
    }

}
