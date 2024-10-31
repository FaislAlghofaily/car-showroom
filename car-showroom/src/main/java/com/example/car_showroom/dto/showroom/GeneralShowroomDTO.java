package com.example.car_showroom.dto.showroom;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GeneralShowroomDTO {
    @NotBlank
    @Size(min = 1, max = 25)
    private String name;
    @NotNull
    @Digits(integer = 10, fraction = 0)
    private Integer crn;
    @Size(min = 1, max = 300)
    private String managerName;
    @Digits(integer = 15, fraction = 0)
    @NotNull
    private Integer contactNumber;
    @Size(min = 1, max = 300)
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCrn() {
        return crn;
    }

    public void setCrn(Integer crn) {
        this.crn = crn;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
