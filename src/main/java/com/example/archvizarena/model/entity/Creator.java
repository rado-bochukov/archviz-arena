package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "creators")
public class Creator extends BaseUser{

    @Column(name = "price_per_image"
    ,nullable = false)
    private BigDecimal pricePerImage;

    @Column(name = "creator_type")
    @Enumerated(EnumType.STRING)
    private CreatorTypeEnum creatorType;
    @OneToMany(mappedBy = "author")
    private List<PortfolioProject> projects;

    @OneToMany
    private List<Comment> commentsSent;

    @OneToMany
    private List<ApplicationMessage> applicationMessagesSent;



    public Creator() {
    }

    public List<Comment> getCommentsSent() {
        return commentsSent;
    }

    public void setCommentsSent(List<Comment> commentsSent) {
        this.commentsSent = commentsSent;
    }

    public List<ApplicationMessage> getApplicationMessagesSent() {
        return applicationMessagesSent;
    }

    public void setApplicationMessagesSent(List<ApplicationMessage> applicationMessagesSent) {
        this.applicationMessagesSent = applicationMessagesSent;
    }

    public List<Comment> getSent() {
        return commentsSent;
    }

    public void setSent(List<Comment> sent) {
        this.commentsSent = sent;
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
