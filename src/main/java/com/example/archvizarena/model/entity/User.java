package com.example.archvizarena.model.entity;


import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private String description;
    @Column
    private String country;

    @Column(name = "user_occupation")
    @Enumerated(EnumType.STRING)
    private UserOccupationEnum userOccupationEnum;
    @Column(name = "price_per_image")
    private BigDecimal pricePerImage;

    @Column(name = "creator_type")
    @Enumerated(EnumType.STRING)
    private CreatorTypeEnum creatorType;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @OneToOne
    private Picture profilePicture;
    @ManyToMany
    private Set<PortfolioProject> likedProjectsId;

    @OneToMany(mappedBy = "buyer",
            fetch = FetchType.EAGER)
    private List<JobPublication> jobPublications;
    @OneToMany(mappedBy = "author")
    private List<PortfolioProject> projects;

    @OneToMany
    private List<Comment> commentsSent;

    @OneToMany
    private List<ApplicationMessage> applicationMessagesSent;




    public User() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UserOccupationEnum getUserOccupationEnum() {
        return userOccupationEnum;
    }

    public void setUserOccupationEnum(UserOccupationEnum userOccupationEnum) {
        this.userOccupationEnum = userOccupationEnum;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserOccupationEnum getUserOccupation() {
        return userOccupationEnum;
    }

    public void setUserOccupation(UserOccupationEnum userOccupationEnum) {
        this.userOccupationEnum = userOccupationEnum;
    }

    public BigDecimal getPricePerImage() {
        return pricePerImage;
    }

    public void setPricePerImage(BigDecimal pricePerImage) {
        this.pricePerImage = pricePerImage;
    }

    public CreatorTypeEnum getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(CreatorTypeEnum creatorType) {
        this.creatorType = creatorType;
    }

    public List<UserRole> getRoles() {
        return userRoles;
    }

    public void setRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<PortfolioProject> getLikedProjectsId() {
        return likedProjectsId;
    }

    public void setLikedProjectsId(Set<PortfolioProject> likedProjectsId) {
        this.likedProjectsId = likedProjectsId;
    }

    public List<JobPublication> getJobPublications() {
        return jobPublications;
    }

    public void setJobPublications(List<JobPublication> jobPublications) {
        this.jobPublications = jobPublications;
    }

    public List<PortfolioProject> getProjects() {
        return projects;
    }

    public void setProjects(List<PortfolioProject> projects) {
        this.projects = projects;
    }

    public List<Comment> getCommentsSent() {
        return commentsSent;
    }

    public void setCommentsSent(List<Comment> commentsSent) {
        this.commentsSent = commentsSent;
    }

    public List<ApplicationMessage> getApplicationMessagesSent() {
        return applicationMessagesSent;
    }

    public void setApplicationMessagesSent(List<ApplicationMessage> applicationMessagesSent) {
        this.applicationMessagesSent = applicationMessagesSent;
    }
}
