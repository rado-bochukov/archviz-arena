package com.example.archvizarena.model.view;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;

import java.math.BigDecimal;

public class JobPublicationBrowseCandidatesViewModel {

    private Long id;
    private String title;
    private String authorName;
    private ProjectCategoryEnum category;
    private BigDecimal budget;


    public JobPublicationBrowseCandidatesViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
