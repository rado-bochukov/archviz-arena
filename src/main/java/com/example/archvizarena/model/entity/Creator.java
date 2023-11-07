package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "creators")
public class Creator extends BaseUser{

<<<<<<< HEAD
    @Column(name = "price_per_image"
    ,nullable = false)
    private BigDecimal pricePerImage;

    @Column(name = "creator_type",
    nullable = false)
=======
    @Column(name = "price_per_image")
    private BigDecimal pricePerImage;

    @Column(name = "creator_type")
>>>>>>> origin/main
    @Enumerated(EnumType.STRING)
    private CreatorTypeEnum creatorType;
    @OneToMany(mappedBy = "author")
    private List<PortfolioProject> projects;

    public Creator() {
    }

    public BigDecimal getPricePerImage() {
        return pricePerImage;
    }

    public void setPricePerImage(BigDecimal pricePerImage) {
        this.pricePerImage = pricePerImage;
    }

    public CreatorTypeEnum getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(CreatorTypeEnum creatorType) {
        this.creatorType = creatorType;
    }

    public List<PortfolioProject> getProjects() {
        return projects;
    }

    public void setProjects(List<PortfolioProject> projects) {
        this.projects = projects;
    }
}
