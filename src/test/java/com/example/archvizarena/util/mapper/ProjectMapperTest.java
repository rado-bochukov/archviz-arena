package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {

    private ProjectMapper testProjectMapper;

    @BeforeEach()
    void setUp(){
        testProjectMapper=new ProjectMapper();

    }

    @Test
    void testMapFromEntity(){
        PortfolioProjectEntity testProject=createProject();
        PictureEntity picture=createPicture();
        UserEntity author=createArtistEntity();

        testProject.setAuthor(author);
        testProject.setPictures(List.of(picture));

        ProjectBrowsingViewModel projectBrowsingViewModel=testProjectMapper.mapFromEntity(testProject);

        Assertions.assertEquals(testProject.getId(),projectBrowsingViewModel.getId());
        Assertions.assertEquals(testProject.getAuthor().getName(),projectBrowsingViewModel.getAuthorName());
        Assertions.assertEquals(testProject.getLikesCount(),projectBrowsingViewModel.getLikesCount());
        Assertions.assertEquals(testProject.getTitle(),projectBrowsingViewModel.getTitle());
    }

    @Test
    void testMapFromEntity_returnNullIfProjectEntity_isNull(){
        PortfolioProjectEntity testProject=null;

        ProjectBrowsingViewModel projectBrowsingViewModel=testProjectMapper.mapFromEntity(testProject);

       Assertions.assertNull(projectBrowsingViewModel);
    }

    @Test
    void testMapFromEntityToReportView(){
        PortfolioProjectEntity testProject=createProject();
        PictureEntity picture=createPicture();
        UserEntity author=createArtistEntity();

        testProject.setAuthor(author);
        testProject.setPictures(List.of(picture));

        ProjectReportViewModel projectReportViewModel=testProjectMapper.mapFromEntityToReportView(testProject);

        Assertions.assertEquals(testProject.getId(),projectReportViewModel.getId());
        Assertions.assertEquals(testProject.getAuthor().getName(),projectReportViewModel.getAuthorName());
        Assertions.assertEquals(testProject.getTitle(),projectReportViewModel.getTitle());
    }






    private static PortfolioProjectEntity createProject() {

        PortfolioProjectEntity project = new PortfolioProjectEntity();
        project.setTitle("Project 1");
        project.setDescription("project 1 description");
        project.setActive(true);
        project.setCategory(ProjectCategoryEnum.EXTERIOR);
        project.setLikesCount(2);
        return project;
    }

    private static UserEntity createArtistEntity() {
        UserEntity artist = new UserEntity();
        artist.setId(1L);
        artist.setName("Artist 1");
        artist.setCountry("Bulgaria");
        artist.setUserOccupation(UserOccupationEnum.ARTIST);
        artist.setCreatorType(CreatorTypeEnum.FREELANCER);
        artist.setDescription("I am artist 1");
        artist.setPricePerImage(BigDecimal.valueOf(350));

        return artist;
    }


    private static PictureEntity createPicture() {
        PictureEntity picture = new PictureEntity();

        picture.setUrl("pictureUrl");
        return picture;
    }



}