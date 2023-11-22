package com.example.archvizarena.model.view;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JobPublicationViewModel {

    private Long id;

    private String title;
    private String authorName;
    private String description;
    private String authorCountry;
    private ProjectCategoryEnum category;
    private BigDecimal budget;

    private List<Long> applicantsId;

    public JobPublicationViewModel() {
        this.applicantsId=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getApplicantsId() {
        return applicantsId;
    }

    public void setApplicantsId(List<Long> applicantsId) {
        this.applicantsId = applicantsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAuthorCountry() {
        return authorCountry;
    }

    public void setAuthorCountry(String authorCountry) {
        this.authorCountry = authorCountry;
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
