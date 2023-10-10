package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@MappedSuperclass
//@Entity
//@Table(name = "users")
public abstract class BaseUser extends BaseEntity {

    @Column (nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String username;
    @Column
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> commentsSend;


    public BaseUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Comment> getCommentsSend() {
        return commentsSend;
    }

    public void setCommentsSend(List<Comment> commentsSend) {
        this.commentsSend = commentsSend;
    }


}
