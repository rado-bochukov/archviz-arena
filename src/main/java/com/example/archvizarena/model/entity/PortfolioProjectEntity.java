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

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "project")
    private List<CommentEntity> comments;

    @Column(name = "likes_count")
    private int likesCount;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserEntity> usersLikedTheProject;


    public PortfolioProjectEntity() {
    }

    public Set<UserEntity> getUsersLikedTheProject() {
        return usersLikedTheProject;
    }

    public void setUsersLikedTheProject(Set<UserEntity> usersLikedTheProject) {
        this.usersLikedTheProject = usersLikedTheProject;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
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
