package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseMessage{


    @ManyToOne
    private PortfolioProjectEntity project;


    public CommentEntity() {
    }

    public PortfolioProjectEntity getProject() {
        return project;
    }

    public void setProject(PortfolioProjectEntity project) {
        this.project = project;
    }




}
