package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "portfolio_projects")
public class PortfolioProject extends BaseProject{

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "project",
            fetch = FetchType.EAGER)
    private List<Picture> pictures;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

    @Column(name = "likes_count")
    private int likeCount;


    public PortfolioProject() {
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
