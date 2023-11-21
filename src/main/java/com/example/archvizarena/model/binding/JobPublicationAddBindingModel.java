package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class JobPublicationAddBindingModel {
    @Size(min=2, max=50)
    @NotEmpty
    private String title;
    @Size( min=5, max = 700)
    @NotEmpty
    private String description;
    @NotNull
    private ProjectCategoryEnum category;
    @Positive
    @NotNull
    private BigDecimal budget;

    public JobPublicationAddBindingModel() {
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
