package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "portfolio_projects")
public class PortfolioProject extends BaseProject{

    @ManyToOne
    private Creator author;

    @OneToMany(mappedBy = "project",
            fetch = FetchType.EAGER)
    private List<Picture> pictures;


    public PortfolioProject() {
    }

    public Creator getAuthor() {
        return author;
    }

    public void setAuthor(Creator author) {
        this.author = author;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
