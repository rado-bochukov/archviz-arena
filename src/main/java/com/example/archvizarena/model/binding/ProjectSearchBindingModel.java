package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;

public class ProjectSearchBindingModel {

    private String priceRange;

    private CreatorTypeEnum creatorType;

    private String country;

    private ProjectCategoryEnum category;

    public ProjectSearchBindingModel() {
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public CreatorTypeEnum getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(CreatorTypeEnum creatorType) {
        this.creatorType = creatorType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ProjectCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(ProjectCategoryEnum category) {
        this.category = category;
    }

    public boolean isEmpty(){
        return country==null &&
                priceRange==null &&
                creatorType==null &&
                category==null;
    }
}
