package com.example.archvizarena.model.entity;


import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;


@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

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
    private List<UserRoleEntity> roles;

    @OneToOne
    private PictureEntity profilePicture;


    @OneToMany(mappedBy = "buyer",
            fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<JobPublicationEntity> jobPublicationEntities;
    @OneToMany(mappedBy = "author")
    private List<PortfolioProjectEntity> projects;

    @OneToMany(mappedBy = "commentAuthor")
    private List<CommentEntity> commentsSent;






    public UserEntity() {

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

    public List<UserRoleEntity> getUserRoles() {
        return roles;
    }

    public void setUserRoles(List<UserRoleEntity> userRoleEntities) {
        this.roles = userRoleEntities;
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

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> userRoleEntities) {
        this.roles = userRoleEntities;
    }

    public PictureEntity getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(PictureEntity profilePicture) {
        this.profilePicture = profilePicture;
    }



    public List<JobPublicationEntity> getJobPublications() {
        return jobPublicationEntities;
    }

    public void setJobPublications(List<JobPublicationEntity> jobPublicationEntities) {
        this.jobPublicationEntities = jobPublicationEntities;
    }

    public List<PortfolioProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<PortfolioProjectEntity> projects) {
        this.projects = projects;
    }

    public List<CommentEntity> getCommentsSent() {
        return commentsSent;
    }

    public void setCommentsSent(List<CommentEntity> commentsSent) {
        this.commentsSent = commentsSent;
    }

}
