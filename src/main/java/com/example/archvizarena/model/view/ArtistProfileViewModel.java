package com.example.archvizarena.model.view;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;

import java.math.BigDecimal;
import java.util.List;

public class ArtistProfileViewModel {

    private Long id;

    private String pictureUrl;

    private String name;
    private String country;

    private CreatorTypeEnum creatorTypeEnum;

    private BigDecimal pricePerImage;

   private List<ProjectBrowsingViewModel> projects;

   private String description;

    public ArtistProfileViewModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CreatorTypeEnum getCreatorTypeEnum() {
        return creatorTypeEnum;
    }

    public void setCreatorTypeEnum(CreatorTypeEnum creatorTypeEnum) {
        this.creatorTypeEnum = creatorTypeEnum;
    }

    public BigDecimal getPricePerImage() {
        return pricePerImage;
    }

    public void setPricePerImage(BigDecimal pricePerImage) {
        this.pricePerImage = pricePerImage;
    }

    public List<ProjectBrowsingViewModel> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectBrowsingViewModel> projects) {
        this.projects = projects;
    }
}
