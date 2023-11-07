package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import jakarta.persistence.*;

<<<<<<< HEAD
import java.time.LocalDateTime;
=======
>>>>>>> origin/main
import java.util.List;
@MappedSuperclass
//@Entity
//@Table(name = "projects")
public abstract class BaseProject extends BaseEntity{
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

<<<<<<< HEAD
    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDateTime publishedOn) {
        this.publishedOn = publishedOn;
    }

    //    private int likeCount;
    @Column(name = "categories")
    @Enumerated(EnumType.STRING)
    private ProjectCategoryEnum category;
=======
//    private int likeCount;
    @Column(name = "categories")
    @Enumerated(EnumType.STRING)
    private List<ProjectCategoryEnum> categories;
>>>>>>> origin/main

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

<<<<<<< HEAD
    public BaseProject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(ProjectCategoryEnum category) {
        this.category = category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
=======





>>>>>>> origin/main
}
