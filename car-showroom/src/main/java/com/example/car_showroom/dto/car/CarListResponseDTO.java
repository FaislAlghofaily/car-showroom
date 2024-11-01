package com.example.car_showroom.dto.car;

import com.example.car_showroom.dto.showroom.ShowroomListResponseDTO;

import java.math.BigDecimal;
import java.util.Date;

public class CarListResponseDTO {
    private ShowroomListResponseDTO showroom;
    private String vin;
    private String status;
    private String maker;
    private String model;
    private Integer modelYear;
    private BigDecimal price;
    private Date createdDate;
    private Date updatedDate;

    public ShowroomListResponseDTO getShowroom() {
        return showroom;
    }

    public void setShowroom(ShowroomListResponseDTO showroom) {
        this.showroom = showroom;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
