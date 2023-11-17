package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "portfolio_projects")
public class PortfolioProjectEntity extends BaseProject{

    @ManyToOne
    private UserEntity author;

    @OneToMany(
            fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<PictureEntity> pictures;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @Column(name = "likes_count")
    private int likeCount;

    @ManyToMany
    private Set<UserEntity> usersLikedTheProject;


    public PortfolioProjectEntity() {
    }

    public Set<UserEntity> getUsersLikedTheProject() {
        return usersLikedTheProject;
    }

    public void setUsersLikedTheProject(Set<UserEntity> usersLikedTheProject) {
        this.usersLikedTheProject = usersLikedTheProject;
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
        return pictures;
    }

    public void setPictures(List<PictureEntity> pictureEntities) {
        this.pictures = pictureEntities;
    }
}
