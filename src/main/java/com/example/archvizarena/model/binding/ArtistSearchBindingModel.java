package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;

import java.math.BigDecimal;

public class ArtistSearchBindingModel {

    private String priceRange;

    private CreatorTypeEnum creatorType;

    private String country;

    public ArtistSearchBindingModel() {
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

    public boolean isEmpty(){
        return country==null &&
                priceRange==null &&
                creatorType==null ;
    }
}
