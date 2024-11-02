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
import com.example.car_showroom.exception.CustomException;
import com.example.car_showroom.repository.ShowroomRepository;
import com.example.car_showroom.service.MessageService;
import com.example.car_showroom.service.ShowroomService;
import com.example.car_showroom.util.CommonUtils;
import com.example.car_showroom.util.MessageHelper;
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
    @Autowired
    private MessageHelper messageHelper;

    /**
     * this method is responsible for creating a new showroom
     *
     * @param acceptedLanguage
     * @param createNewShowroomRequestDTO
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponseDTO> createNewShowroom(String acceptedLanguage, CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        validateCreateShowroomRequest(acceptedLanguage, createNewShowroomRequestDTO);
        createAndSaveShowroom(createNewShowroomRequestDTO);
        return new ResponseEntity<>(messageHelper.getSuccessResponse(acceptedLanguage, MessageConstant.CREATE_SHOWROOM_SUCCESS), HttpStatus.OK);
    }

    /**
     * this method is responsible for getting showrooms based on filters and sort based on sortBy param
     *
     * @param acceptedLanguage
     * @param requestParams
     * @param pageNumber
     * @param pageLimit
     * @param sortBy
     * @param sortType
     * @return
     */
    @Override
    public ResponseEntity<Object> getShowrooms(String acceptedLanguage, Map<String, String> requestParams, int pageNumber, int pageLimit, String sortBy, String sortType) {
        Page<Showroom> showrooms = getShowroomsWithFilters(PageRequest.of((pageNumber - 1), pageLimit), requestParams, sortBy, sortType);
        Object response = null;
        if (Optional.ofNullable(showrooms).isPresent() && !CollectionUtils.isEmpty(showrooms.getContent())) {
            response = getShowroomResponse(showrooms.getContent());
        }
        return pageableResponseConverter.getPageableResponse(response, showrooms, pageLimit, pageNumber);

    }

    /**
     * this method is responsible for getting a showroom based on CRN
     *
     * @param acceptedLanguage
     * @param crn
     * @return
     */
    @Override
    public ResponseEntity<ShowroomFiltersDTO> getShowroomWithCRN(String acceptedLanguage, String crn) {
        ShowroomFiltersDTO showroomFiltersDTO = new ShowroomFiltersDTO();
        Showroom showroom = Optional.ofNullable(showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE)).orElseThrow(() ->
                new CustomException(acceptedLanguage, ErrorMessageConstant.NO_SHOWROOM_FOUND));
        showroomFiltersDTO.setCrn(crn);
        showroomFiltersDTO.setName(showroom.getName());
        showroomFiltersDTO.setAddress(showroom.getAddress());
        showroomFiltersDTO.setManagerName(showroom.getManagerName());
        showroomFiltersDTO.setContactNumber(String.valueOf(showroom.getContactNumber()));

        return new ResponseEntity(showroomFiltersDTO, HttpStatus.OK);
    }

    /**
     * this method is responsible for updating showroom's address, contactNumber, managerName
     *
     * @param acceptedLanguage
     * @param crn
     * @param updateShowroomRequestDTO
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponseDTO> updateShowroom(String acceptedLanguage, String crn, UpdateShowroomRequestDTO updateShowroomRequestDTO) {
        validateUpdateRequest(acceptedLanguage, updateShowroomRequestDTO, crn);
        Showroom showroom = Optional.ofNullable(showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE)).orElseThrow(() ->
                new CustomException(acceptedLanguage, ErrorMessageConstant.NO_SHOWROOM_FOUND));
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
        return new ResponseEntity<>(messageHelper.getSuccessResponse(acceptedLanguage, MessageConstant.UPDATE_SHOWROOM_SUCCESS), HttpStatus.OK);
    }

    /**
     * this method is responsible for inactivating showroom
     *
     * @param acceptedLanguage
     * @param crn
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponseDTO> inactivateShowroom(String acceptedLanguage, String crn) {
        if (!crn.matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid CRN" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.INVALID_CRN);
        }
        if (!showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(crn))) {
            logger.error("Error while creating showroom duplicate CRN:" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.NO_SHOWROOM_FOUND);
        }
        Showroom showroom = Optional.ofNullable(showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE)).orElseThrow(() ->
                new CustomException(acceptedLanguage, ErrorMessageConstant.NO_SHOWROOM_FOUND));
        showroom.setStatus(ApplicationConstants.INACTIVE);
        showroomRepository.save(showroom);
        return new ResponseEntity<>(messageHelper.getSuccessResponse(acceptedLanguage, MessageConstant.INACTIVATE_SHOWROOM_SUCCESS), HttpStatus.OK);
    }

    private Showroom createAndSaveShowroom(CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        Showroom showroom = new Showroom();
        BeanUtils.copyProperties(createNewShowroomRequestDTO, showroom);
        showroom.setCreatedDate(new Date());
        showroom.setUpdatedDate(new Date());
        showroom.setStatus(ApplicationConstants.ACTIVE);
        showroom.setCommercialRegistrationNumber(Long.valueOf(createNewShowroomRequestDTO.getCrn()));
        if (!StringUtils.isBlank(createNewShowroomRequestDTO.getContactNumber())) {
            showroom.setContactNumber(Long.valueOf(createNewShowroomRequestDTO.getContactNumber()));
        }
        return showroomRepository.save(showroom);
    }

    /**
     * this method is responsible for validating createNewShowroomRequestDTO
     *
     * @param acceptedLanguage
     * @param createNewShowroomRequestDTO
     */
    private void validateCreateShowroomRequest(String acceptedLanguage, CreateNewShowroomRequestDTO createNewShowroomRequestDTO) {
        if (!createNewShowroomRequestDTO.getCrn().matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid CRN" + createNewShowroomRequestDTO.getCrn());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.INVALID_CRN);
        }
        if (showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(createNewShowroomRequestDTO.getCrn()))) {
            logger.error("Error while creating showroom duplicate CRN:" + createNewShowroomRequestDTO.getCrn());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!StringUtils.isBlank(createNewShowroomRequestDTO.getContactNumber()) && !createNewShowroomRequestDTO.getContactNumber().matches(ApplicationConstants.MAX_15_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + createNewShowroomRequestDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.INVALID_CRN);
        }
    }

    /**
     * this method is responsible for fetching showrooms using dynamic filters then sort them based on sortBy param
     *
     * @param pageRequest
     * @param requestParams
     * @param sortBy
     * @param sortType
     * @return
     */
    private Page<Showroom> getShowroomsWithFilters(PageRequest pageRequest, Map<String, String> requestParams, String sortBy, String sortType) {
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
            predicates.add(criteriaBuilder.equal(root.get(ApplicationConstants.STATUS), ApplicationConstants.ACTIVE.toLowerCase()));

            query.distinct(true);
            query.multiselect(root.get(ApplicationConstants.FILTER_NAME), root.get(ApplicationConstants.FILTER_CRN), root.get(ApplicationConstants.FILTER_CONTACT_NUMBER));
            if (StringUtils.isBlank(sortBy)) {
                if (sortType.equalsIgnoreCase(ApplicationConstants.SORT_ASC)) {
                    query.orderBy(criteriaBuilder.asc(root.get(ApplicationConstants.CREATED_DATE)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(ApplicationConstants.CREATED_DATE)));
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
            ShowroomListResponseDTO showroomListResponseDTO = CommonUtils.createObjectMapper().convertValue(showroom, ShowroomListResponseDTO.class);
            showroomsListDTO.add(showroomListResponseDTO);
        });
        return showroomsListDTO;
    }

    private void validateUpdateRequest(String acceptedLanguage, UpdateShowroomRequestDTO updateShowroomRequestDTO, String crn) {
        if (!crn.matches(ApplicationConstants.EXACTLY_10_DIGITS_REGEX)) {
            logger.error("Invalid CRN" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.INVALID_CRN);
        }
        if (!showroomRepository.existsByCommercialRegistrationNumber(Long.valueOf(crn))) {
            logger.error("Error while creating showroom duplicate CRN:" + crn);
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.DUPLICATE_SHOWROOM_ERROR);
        }
        if (!StringUtils.isBlank(updateShowroomRequestDTO.getContactNumber()) && !updateShowroomRequestDTO.getContactNumber().matches(ApplicationConstants.MAX_15_DIGITS_REGEX)) {
            logger.error("Invalid Contact Number" + updateShowroomRequestDTO.getContactNumber());
            throw new CustomException(acceptedLanguage, ErrorMessageConstant.INVALID_CONTACT_NUMBER);
        }
    }

}
