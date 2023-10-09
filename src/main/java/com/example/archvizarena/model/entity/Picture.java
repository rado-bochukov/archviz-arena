package com.example.archvizarena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {
    @Column(name = "picture_url")
    private String url;

    @ManyToOne
    private Project project;

    @ManyToOne
    private UserEntity author;

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }
}
