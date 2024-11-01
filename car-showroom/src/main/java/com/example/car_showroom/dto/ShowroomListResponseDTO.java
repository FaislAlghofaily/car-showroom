package com.example.car_showroom.dto;

public class ShowroomListResponseDTO {
    private String name;
    private String commercialRegistrationNumber;
    private Long contactNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommercialRegistrationNumber() {
        return commercialRegistrationNumber;
    }

    public void setCommercialRegistrationNumber(String commercialRegistrationNumber) {
        this.commercialRegistrationNumber = commercialRegistrationNumber;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }
}
