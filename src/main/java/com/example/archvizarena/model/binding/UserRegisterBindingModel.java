package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.util.validation.UniqueUserName;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UserRegisterBindingModel {
    @Size(min = 2, max = 50)
    @NotEmpty
    private String name;
    @Email
    private String email;
    @Size(min = 2, max = 20,
            message = "Username should be between 2 and 20 symbols")
    @NotEmpty(message = "Username should be provided")
    @UniqueUserName(message = "Username already exists!")
    private String username;
    @Size(min = 2, max = 20)
    @NotNull
    private String password;
    @Size(max = 500)
    private String description;
    @NotEmpty
    private String country;
    @NotNull
    private UserOccupationEnum userOccupation;
    @NotNull
    private CreatorTypeEnum creatorType;
    @Positive
    @NotNull
    private BigDecimal pricePerImage;

    public UserRegisterBindingModel() {
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
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
