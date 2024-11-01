package com.example.car_showroom.service.impl;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.dto.GeneralResponseDTO;
import com.example.car_showroom.dto.car.CarListResponseDTO;
import com.example.car_showroom.dto.car.CreateCarRequestDTO;
import com.example.car_showroom.entity.Car;
import com.example.car_showroom.entity.Showroom;
import com.example.car_showroom.enums.CarStatusEnum;
import com.example.car_showroom.exception.CustomException;
import com.example.car_showroom.repository.CarRepository;
import com.example.car_showroom.repository.ShowroomRepository;
import com.example.car_showroom.service.CarService;
import com.example.car_showroom.util.CommonUtils;
import com.example.car_showroom.util.MessageHelper;
import com.example.car_showroom.util.PageableResponseConverter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private PageableResponseConverter pageableResponseConverter;

    /**
     * this method is responsible for creating new car
     * @param acceptedLanguage
     * @param crn
     * @param createCarRequestDTO
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponseDTO> createNewCarInShowroom(String acceptedLanguage, String crn, CreateCarRequestDTO createCarRequestDTO) {
        Showroom showroom = Optional.ofNullable(showroomRepository.findByCommercialRegistrationNumberAndStatus(Long.valueOf(crn), ApplicationConstants.ACTIVE)).orElseThrow(() ->
                new CustomException(acceptedLanguage, "Showroom not found"));
        createAndSaveNewCar(createCarRequestDTO, showroom);
        return new ResponseEntity<>(messageHelper.getSuccessResponse(acceptedLanguage, "message"), HttpStatus.OK);
    }

    /**
     * this method is responsible for getting cars with dynamic filters and sorting
     * @param acceptedLanguage
     * @param requestParams
     * @param pageNumber
     * @param pageLimit
     * @param sortBy
     * @param sortType
     * @return
     */
    @Override
    public ResponseEntity<Object> getCarsWithFilters(String acceptedLanguage, Map<String, String> requestParams, int pageNumber, int pageLimit, String sortBy, String sortType) {
        Page<Car> cars = getCarsListWithFilters(PageRequest.of((pageNumber - 1), pageLimit), requestParams, sortBy, sortType);
        Object response = null;
        if (Optional.ofNullable(cars).isPresent() && !CollectionUtils.isEmpty(cars.getContent())) {
            response = getCarResponse(cars.getContent());
        }
        return pageableResponseConverter.getPageableResponse(response, cars, pageLimit, pageNumber);
    }

    /**
     * this method for creating and saving a new car
     * @param createCarRequestDTO
     * @param showroom
     * @return
     */

    private Car createAndSaveNewCar(CreateCarRequestDTO createCarRequestDTO, Showroom showroom) {
        Car car = new Car();
        BeanUtils.copyProperties(createCarRequestDTO, car);
        car.setShowroom(showroom);
        car.setStatus(CarStatusEnum.AVAILABLE.getStatus());
        return carRepository.save(car);
    }

    /**
     * this method uses criteriaBuilder to build a query that will fetch data based on requested filters
     * then will sort based on sortBy param
     * @param pageRequest
     * @param requestParams
     * @param sortBy
     * @param sortType
     * @return
     */
    private Page<Car> getCarsListWithFilters(PageRequest pageRequest, Map<String, String> requestParams, String sortBy, String sortType) {

        return carRepository.findAll((Specification<Car>) (root, query, criteriaBuilder) -> {
            Join<Car, Showroom> showroomJoin;
            showroomJoin = root.join("showroom", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_NAME))) {
                predicates.add(criteriaBuilder.like(showroomJoin.get(ApplicationConstants.FILTER_NAME), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_NAME) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_CRN))) {
                predicates.add(criteriaBuilder.like(showroomJoin.get(ApplicationConstants.FILTER_CRN).as(String.class), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_CRN) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_CONTACT_NUMBER))) {
                predicates.add(criteriaBuilder.like(showroomJoin.get(ApplicationConstants.FILTER_CONTACT_NUMBER).as(String.class), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_CONTACT_NUMBER) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_MAKER))) {
                predicates.add(criteriaBuilder.like(showroomJoin.get(ApplicationConstants.FILTER_MAKER), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_MAKER) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_MODEL))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_MODEL), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_MODEL) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_VIN))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_VIN), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_VIN) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_MODEL_YEAR))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_MODEL_YEAR).as(String.class), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_MODEL_YEAR) + ApplicationConstants.STRING_PERCENTAGE));
            }
            if (StringUtils.isNotBlank(requestParams.get(ApplicationConstants.FILTER_PRICE))) {
                predicates.add(criteriaBuilder.like(root.get(ApplicationConstants.FILTER_PRICE).as(String.class), ApplicationConstants.STRING_PERCENTAGE + requestParams.get(ApplicationConstants.FILTER_PRICE) + ApplicationConstants.STRING_PERCENTAGE));
            }
            predicates.add(criteriaBuilder.equal(showroomJoin.get(ApplicationConstants.STATUS), ApplicationConstants.ACTIVE.toLowerCase()));

            query.distinct(true);
            if (StringUtils.isBlank(sortBy)) {
                if (sortType.equalsIgnoreCase(ApplicationConstants.SORT_ASC)) {
                    query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("createdDate")));
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

    private Object getCarResponse(List<Car> cars) {
        List<CarListResponseDTO> carListResponseDTO = new ArrayList<>();
        cars.forEach(car -> {
            CarListResponseDTO carListResponse = CommonUtils.createObjectMapper().convertValue(car, CarListResponseDTO.class);
            carListResponseDTO.add(carListResponse);
        });
        return carListResponseDTO;
    }
}
