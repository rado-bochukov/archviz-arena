package com.example.archvizarena.model.view;

import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;

import java.math.BigDecimal;
import java.util.List;

public class ProjectDetailsViewModel {
    private Long id;

    private String title;

    private String description;

    private String authorName;

    private List<String> imagesUrls;

    private int likesCount;

    private BigDecimal pricePerImage;

    private ProjectCategoryEnum category;

    private boolean isLikedFromCurrentUser;

    private boolean isViewerTheOwner;

    private List<CommentViewModel> projectComments;

    public ProjectDetailsViewModel() {
    }

    public boolean isViewerTheOwner() {
        return isViewerTheOwner;
    }

    public void setViewerTheOwner(boolean viewerTheOwner) {
        isViewerTheOwner = viewerTheOwner;
    }

    public List<CommentViewModel> getProjectComments() {
        return projectComments;
    }

    public void setProjectComments(List<CommentViewModel> projectComments) {
        this.projectComments = projectComments;
    }

    public boolean isLikedFromCurrentUser() {
        return isLikedFromCurrentUser;
    }

    public void setLikedFromCurrentUser(boolean likedFromCurrentUser) {
        isLikedFromCurrentUser = likedFromCurrentUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public BigDecimal getPricePerImage() {
        return pricePerImage;
    }

    public void setPricePerImage(BigDecimal pricePerImage) {
        this.pricePerImage = pricePerImage;
    }

    public ProjectCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(ProjectCategoryEnum category) {
        this.category = category;
    }


}
