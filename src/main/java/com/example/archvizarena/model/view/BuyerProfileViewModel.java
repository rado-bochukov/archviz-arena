package com.example.archvizarena.model.view;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;

import java.math.BigDecimal;
import java.util.List;

public class BuyerProfileViewModel {
    private Long id;

    private String pictureUrl;

    private String name;
    private String country;

    private CreatorTypeEnum creatorTypeEnum;

    private BigDecimal budget;

    private List<JobPublicationViewModel> publishedJobs;

    private String description;

    public BuyerProfileViewModel() {
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

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public List<JobPublicationViewModel> getPublishedJobs() {
        return publishedJobs;
    }

    public void setPublishedJobs(List<JobPublicationViewModel> publishedJobs) {
        this.publishedJobs = publishedJobs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
