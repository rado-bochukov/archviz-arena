package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity{
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

//    private int likeCount;
    @Column(name = "categories")
    @Enumerated(EnumType.STRING)
    private List<ProjectCategoryEnum> categories;

    @ManyToOne
    private UserEntity author;

    @OneToMany(mappedBy = "project",
    fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "project",
            fetch = FetchType.EAGER)
    private List<Picture> pictures;




}
