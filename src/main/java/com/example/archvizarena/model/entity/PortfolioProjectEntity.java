package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "portfolio_projects")
public class PortfolioProjectEntity extends BaseProject{

    @ManyToOne
    private UserEntity author;

    @OneToMany(mappedBy = "project",
            fetch = FetchType.EAGER)
    private List<PictureEntity> pictureEntities;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CommentEntity> comments;

    @Column(name = "likes_count")
    private int likeCount;


    public PortfolioProjectEntity() {
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public List<PictureEntity> getPictures() {
        return pictureEntities;
    }

    public void setPictures(List<PictureEntity> pictureEntities) {
        this.pictureEntities = pictureEntities;
    }
}
