package com.example.archvizarena.testUtils;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.model.service.CommentAddServiceModel;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.service.ReportSubmitServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.model.view.UserProfileViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class UnitTestUtil {

    public static UserProfileViewModel testArtistProfile() {
        UserProfileViewModel artistProfileViewModel = new UserProfileViewModel();
        artistProfileViewModel.setId(1L);
        artistProfileViewModel.setName("Artist 1");
        artistProfileViewModel.setCountry("Bulgaria");
        artistProfileViewModel.setUserOccupation(UserOccupationEnum.ARTIST);
        artistProfileViewModel.setCreatorTypeEnum(CreatorTypeEnum.FREELANCER);
        artistProfileViewModel.setDescription("I am artist 1");
        artistProfileViewModel.setPricePerImage(BigDecimal.valueOf(350));
        artistProfileViewModel.setViewerIsOwner(false);
        artistProfileViewModel.setPictureUrl(createProfilePicture().getUrl());
        return artistProfileViewModel;

    }

    public static UserProfileViewModel testBuyerProfile() {
        UserProfileViewModel buyerProfileViewModel = new UserProfileViewModel();
        buyerProfileViewModel.setId(2L);
        buyerProfileViewModel.setName("Buyer 1");
        buyerProfileViewModel.setCountry("France");
        buyerProfileViewModel.setUserOccupation(UserOccupationEnum.BUYER);
        buyerProfileViewModel.setDescription("I am buyer 1");
        buyerProfileViewModel.setViewerIsOwner(false);
        return buyerProfileViewModel;

    }

    public static UserEntity createArtistEntity() {
        UserEntity artist = new UserEntity();
        artist.setId(1L);
        artist.setName("Artist 1");
        artist.setCountry("Bulgaria");
        artist.setUsername("artist1");
        artist.setUserOccupation(UserOccupationEnum.ARTIST);
        artist.setCreatorType(CreatorTypeEnum.FREELANCER);
        artist.setDescription("I am artist 1");
        artist.setPricePerImage(BigDecimal.valueOf(350));
        artist.setJobPublications(new ArrayList<>());

        return artist;
    }

    public static UserEditBindingModel editArtistEntity() {
        UserEditBindingModel artistEdit = new UserEditBindingModel();
        artistEdit.setId(1L);
        artistEdit.setName("Artist 11");
        artistEdit.setCountry("France");
        artistEdit.setUserOccupation(UserOccupationEnum.ARTIST);
        artistEdit.setCreatorType(CreatorTypeEnum.FREELANCER);
        artistEdit.setDescription("I am artist 1");
        artistEdit.setPricePerImage(BigDecimal.valueOf(350));


        return artistEdit;
    }


    public static UserEntity createBuyerEntity() {
        UserEntity buyer = new UserEntity();
        buyer.setId(2L);
        buyer.setName("Buyer 1");
        buyer.setCountry("France");
        buyer.setUserOccupation(UserOccupationEnum.BUYER);
        buyer.setDescription("I am buyer 1");
        buyer.setJobPublications(new ArrayList<>());

        return buyer;
    }

    public static PortfolioProjectEntity createProject() {

        PortfolioProjectEntity project = new PortfolioProjectEntity();
        project.setTitle("Project 1");
        project.setDescription("project 1 description");
        project.setActive(true);
        project.setCategory(ProjectCategoryEnum.EXTERIOR);
        project.setLikesCount(0);
        project.setUsersLikedTheProject(new HashSet<>());
        project.setComments(new ArrayList<>());
        return project;
    }

    public static ProjectDetailsViewModel projectDetailsViewModel() {

        ProjectDetailsViewModel projectDetailsViewModel = new ProjectDetailsViewModel();
        projectDetailsViewModel.setTitle("Project 1");
        projectDetailsViewModel.setDescription("project 1 description");
        projectDetailsViewModel.setCategory(ProjectCategoryEnum.EXTERIOR);
        projectDetailsViewModel.setLikesCount(0);
        return projectDetailsViewModel;
    }

    public static PortfolioProjectServiceModel portfolioProjectServiceModel() {

        PortfolioProjectServiceModel portfolioToBeSaved = new PortfolioProjectServiceModel();
        portfolioToBeSaved.setTitle("Project 1");
        portfolioToBeSaved.setDescription("project 1 description");
        portfolioToBeSaved.setCategory(ProjectCategoryEnum.EXTERIOR);

        return portfolioToBeSaved;
    }

    public static JobPublicationEntity createJobPublication() {

        JobPublicationEntity job = new JobPublicationEntity();
        job.setTitle("Job_p 1");
        job.setDescription("job 1 description");
        job.setActive(true);
        job.setCategory(ProjectCategoryEnum.EXTERIOR);
        job.setBudget(BigDecimal.valueOf(1000));

        return job;
    }

    public static PictureEntity createPicture() {
        PictureEntity picture = new PictureEntity();

        picture.setUrl("pictureUrl");
        picture.setId(1L);
        return picture;
    }

    public static PictureEntity createProfilePicture() {
        PictureEntity picture = new PictureEntity();

        picture.setUrl("ProfilePictureUrl");
        picture.setId(2L);
        return picture;
    }

    public static ReportEntity createReport() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setMessage("This is test Report Message");
        return reportEntity;
    }

    public static ReportSubmitServiceModel createReportSubmitServiceModel() {
        ReportSubmitServiceModel reportSubmit = new ReportSubmitServiceModel();
        reportSubmit.setMessage("This is test Report Message");
        return reportSubmit;
    }

    public static WorkInProgressEntity createWorkInProgress() {
        WorkInProgressEntity workInProgress = new WorkInProgressEntity();
        workInProgress.setId(1L);
        return workInProgress;
    }

    public static MessageEntity createMessage() {
        MessageEntity message = new MessageEntity();
        message.setTextContent("This is test message");
        message.setCreated(LocalDateTime.now());
        return message;
    }


    public static ApplicationEntity createApplication() {
        ApplicationEntity application = new ApplicationEntity();
        application.setId(1L);
        application.setTextContent("Test application message");
        return application;
    }

    public static ApplicationAddServiceModel createApplicationServiceModel() {
        ApplicationAddServiceModel applicationToBeAdded = new ApplicationAddServiceModel();
        applicationToBeAdded.setTextContent("Test application message");
        return applicationToBeAdded;
    }

    public static CommentEntity createComment(){
        CommentEntity comment=new CommentEntity();
        comment.setId(1L);
        comment.setTextContent("This is test comment.");
        return comment;
    }

    public static CommentAddServiceModel createCommentServiceModel(){
        CommentAddServiceModel commentToBeAdded=new CommentAddServiceModel();
        commentToBeAdded.setTextContent("This is test comment.");
        return commentToBeAdded;
    }


}
