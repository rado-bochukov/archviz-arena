package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.util.validation.UniqueUserName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UserEditBindingModel {

    private Long id;


    @Size(min = 2, max = 50)
    @NotEmpty
    private String name;

    private String email;
    @Size(min = 2, max = 20,
            message = "Username should be between 2 and 20 symbols!")
    @NotEmpty(message = "Username should be provided!")
    private String username;

    @Size(max = 500)
    private String description;
    @NotEmpty
    private String country;

    private CreatorTypeEnum creatorType;
    private BigDecimal pricePerImage;

    private UserOccupationEnum userOccupation;

    private String profilePicture;

    public UserEditBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserOccupationEnum getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(UserOccupationEnum userOccupation) {
        this.userOccupation = userOccupation;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
