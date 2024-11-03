package com.example.car_showroom.dto.car;

import com.example.car_showroom.constant.ErrorMessageConstant;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateCarRequestDTO {
    @NotNull(message = ErrorMessageConstant.MUST_NOT_BE_NULL)
    @Column(name = "vin")
    @Size(min = 1, max = 25)
    private String vin;
    @NotNull(message = ErrorMessageConstant.MUST_NOT_BE_NULL)
    @Size(min = 1, max = 25)
    @Column(name = "maker")
    private String maker;
    @NotNull(message = ErrorMessageConstant.MUST_NOT_BE_NULL)
    @Column(name = "model")
    @Size(min = 1, max = 25)
    private String model;

    @NotNull(message = ErrorMessageConstant.MUST_NOT_BE_NULL)
    @Column(name = "model_year")
    @Digits(integer = 4, fraction = 0)
    private Integer modelYear;

    @Column(name = "price")
    @Positive
    private BigDecimal price;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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
}
