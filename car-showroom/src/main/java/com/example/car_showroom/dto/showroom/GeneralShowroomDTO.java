package com.example.car_showroom.dto.showroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GeneralShowroomDTO {
    @NotBlank
    @Size(min = 1, max = 25)
    private String name;
    @Size(min = 10, max = 10)
    @NotBlank
    private String crn;
    @Size(min = 1, max = 300)
    private String managerName;
    @Size(min = 1, max = 15)
    private String contactNumber;
    @Size(min = 1, max = 300)
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
