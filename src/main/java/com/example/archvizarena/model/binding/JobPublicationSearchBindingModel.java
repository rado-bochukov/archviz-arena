package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;

public class JobPublicationSearchBindingModel {

    private String priceRange;


    private String country;

    private ProjectCategoryEnum category;

    public JobPublicationSearchBindingModel() {
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
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
                category==null;
    }

}
