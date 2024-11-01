package com.example.car_showroom.repository;

import com.example.car_showroom.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Integer>, JpaSpecificationExecutor<Showroom> {
    boolean existsByCommercialRegistrationNumber(Long commercialRegistrationNumber);
    Showroom findByCommercialRegistrationNumberAndStatus(Long commercialRegistrationNumber, String status);

}
