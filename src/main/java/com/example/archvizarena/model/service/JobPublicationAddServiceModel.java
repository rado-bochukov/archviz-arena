package com.example.archvizarena.model.service;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;

import java.math.BigDecimal;

public class JobPublicationAddServiceModel {



    private String title;

    private String description;

    private ProjectCategoryEnum category;

    private BigDecimal budget;

    public JobPublicationAddServiceModel() {
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

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
