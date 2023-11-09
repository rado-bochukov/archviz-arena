package com.example.archvizarena.model.entity;


import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//@Entity
//@Table(name = "users")
@MappedSuperclass
public abstract class BaseUser extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String password;
    @Column
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToOne
    private Picture profilePicture;

    private Set<Long> likedProjectsId;

    public Set<Long> getLikedProjectsId() {
        return likedProjectsId;
    }

    public void setLikedProjectsId(Set<Long> likedProjectsId) {
        this.likedProjectsId = likedProjectsId;
    }

    public BaseUser() {
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
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
}
