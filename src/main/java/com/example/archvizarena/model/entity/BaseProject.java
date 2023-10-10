package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import jakarta.persistence.*;

import java.util.List;
@MappedSuperclass
//@Entity
//@Table(name = "projects")
public abstract class BaseProject extends BaseEntity{
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

//    private int likeCount;
    @Column(name = "categories")
    @Enumerated(EnumType.STRING)
    private List<ProjectCategoryEnum> categories;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;






}
