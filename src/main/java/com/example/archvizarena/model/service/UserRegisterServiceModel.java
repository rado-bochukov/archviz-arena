package com.example.archvizarena.model.service;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.util.validation.UniqueUserName;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UserRegisterServiceModel {

    private String name;

    private String email;

    private String username;

    private String password;

    private String description;

    private String country;

    private UserOccupationEnum userOccupation;

    private CreatorTypeEnum creatorType;

    private BigDecimal pricePerImage;

    public UserRegisterServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        country = country;
    }

    public UserOccupationEnum getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(UserOccupationEnum userOccupation) {
        this.userOccupation = userOccupation;
    }

    public CreatorTypeEnum getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(CreatorTypeEnum creatorType) {
        this.creatorType = creatorType;
    }

    public BigDecimal getPricePerImage() {
        return pricePerImage;
    }

    public void setPricePerImage(BigDecimal pricePerImage) {
        this.pricePerImage = pricePerImage;
    }
}
