package com.example.archvizarena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {
    @Column(name = "picture_url")
    private String url;

    @ManyToOne
    private PortfolioProjectEntity project;

//    @ManyToOne
//    private User author;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PortfolioProjectEntity getProject() {
        return project;
    }

    public void setProject(PortfolioProjectEntity project) {
        this.project = project;
    }

//    public User getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(User author) {
//        this.author = author;
//    }
}
