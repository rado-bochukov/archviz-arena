package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PortfolioProjectBindingModel {
    @NotNull
    @NotEmpty
    private String title;

    private String description;
    @NotNull
    private ProjectCategoryEnum category;

    private List<String> picturesUrl;


    public PortfolioProjectBindingModel() {
        picturesUrl = new ArrayList<>();
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
