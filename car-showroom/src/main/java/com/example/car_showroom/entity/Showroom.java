package com.example.car_showroom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.Date;

@Entity
@Table(name = "showrooms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Showroom {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(name = "name")
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    @Column(name = "commercial_registration_number")
    @Digits(integer = 10, fraction = 0)
    private Long commercialRegistrationNumber;
    @Column(name = "manager_name")
    @Size(min = 1, max = 100)
    private String managerName;
    @NotNull
    @Column(name = "contact_number")
    @Digits(integer = 15, fraction = 0)
    private Long contactNumber;
    @Column(name = "address")
    @Size(min = 1, max = 255)
    private String address;
    @NotNull
    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCommercialRegistrationNumber() {
        return commercialRegistrationNumber;
    }

    public void setCommercialRegistrationNumber(Long commercialRegistrationNumber) {
        this.commercialRegistrationNumber = commercialRegistrationNumber;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
