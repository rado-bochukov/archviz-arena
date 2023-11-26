package com.example.archvizarena.model.view;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;

import java.math.BigDecimal;
import java.util.List;

public class UserProfileViewModel {

    private Long id;

    private String pictureUrl;

    private String name;
    private String country;

    private CreatorTypeEnum creatorTypeEnum;
    private UserOccupationEnum userOccupation;

    private BigDecimal pricePerImage;

    private List<ProjectBrowsingViewModel> projects;

    private  List<JobPublicationViewModel> activeJobPublications;
    private  List<JobPublicationViewModel> inactiveJobPublications;

    private List<WorkInProgressViewModel> workInProgress;

    private BigDecimal budget;

    private String description;

    private boolean viewerIsOwner;

    public UserProfileViewModel() {
    }

    public UserOccupationEnum getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(UserOccupationEnum userOccupation) {
        this.userOccupation = userOccupation;
    }

    public boolean isViewerIsOwner() {
        return viewerIsOwner;
    }

    public void setViewerIsOwner(boolean viewerIsOwner) {
        this.viewerIsOwner = viewerIsOwner;
    }

    public List<WorkInProgressViewModel> getWorkInProgress() {
        return workInProgress;
    }

    public void setWorkInProgress(List<WorkInProgressViewModel> workInProgress) {
        this.workInProgress = workInProgress;
    }

    public List<JobPublicationViewModel> getActiveJobPublications() {
        return activeJobPublications;
    }

    public void setActiveJobPublications(List<JobPublicationViewModel> activeJobPublications) {
        this.activeJobPublications = activeJobPublications;
    }

    public List<JobPublicationViewModel> getInactiveJobPublications() {
        return inactiveJobPublications;
    }

    public void setInactiveJobPublications(List<JobPublicationViewModel> inactiveJobPublications) {
        this.inactiveJobPublications = inactiveJobPublications;
    }


    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
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
