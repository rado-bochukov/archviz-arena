package com.example.archvizarena.model.service;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;


import java.util.List;

public class PortfolioProjectServiceModel {


    private String title;
    private String description;
    private ProjectCategoryEnum category;
    private List<String> picturesUrl;



    public PortfolioProjectServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(ProjectCategoryEnum category) {
        this.category = category;
    }

    public List<String> getPicturesUrl() {
        return picturesUrl;
    }

    public void setPicturesUrl(List<String> picturesUrl) {
        this.picturesUrl = picturesUrl;
    }


}
