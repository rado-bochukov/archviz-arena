package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.CommentService;
import com.example.archvizarena.service.ProjectService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.testUtils.UnitTestUtil;
import com.example.archvizarena.util.mapper.ProjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    private ProjectService projectServiceToTest;

    @Mock
    private  ProjectRepository projectRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  ModelMapper modelMapper;
    @Mock
    private  PictureRepository pictureRepository;
    @Mock
    private  CommentService commentService;

    private  ProjectMapper projectMapper;
    @Mock
    private ArchVizArenaUserDetails archVizArenaUserDetails;





    @BeforeEach
    void setUp(){
        projectServiceToTest=new ProjectServiceImpl(projectRepository,
                userRepository,
                modelMapper,
                pictureRepository,
                commentService,
               new ProjectMapper());
    }

    @Test
    void testProjectNotFound(){
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> projectServiceToTest.findById(2L,null));
    }

    @Test
    void testSavePortfolioProject(){
        PortfolioProjectServiceModel portfolioProjectServiceModel=portfolioProjectServiceModel();
        portfolioProjectServiceModel.setPicturesUrl(List.of(createPicture().getUrl()));
        PortfolioProjectEntity testProject=createProject();
        UserEntity author= createArtistEntity();
        List<PictureEntity> projectPictures= List.of(createPicture());
        testProject.setPictures(projectPictures);
        testProject.setAuthor(author);

        when(userRepository.findByUsername(author.getUsername())).thenReturn(Optional.of(author));
        when(pictureRepository.findByUrl(projectPictures.get(0).getUrl())).thenReturn(projectPictures.get(0));
        when(projectRepository.save(testProject)).thenReturn(testProject);
        when(modelMapper.map(portfolioProjectServiceModel,PortfolioProjectEntity.class)).thenReturn(testProject);

        projectServiceToTest.saveProject(portfolioProjectServiceModel,author.getUsername());

        verify(userRepository, times(1)).findByUsername(author.getUsername());
        verify(pictureRepository, times(1)).findByUrl(projectPictures.get(0).getUrl());
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void testLikeTheProject(){

        ProjectDetailsViewModel projectWithIncreasedLikes=projectDetailsViewModel();
        PortfolioProjectEntity testProject=createProject();
        projectWithIncreasedLikes.setLikesCount(testProject.getLikesCount()+1);

        UserEntity author= createArtistEntity();
        List<PictureEntity> projectPictures= List.of(createPicture());
        testProject.setPictures(projectPictures);
        testProject.setAuthor(author);

        when(projectRepository.findById(testProject.getId())).thenReturn(Optional.of(testProject));
        when(archVizArenaUserDetails.getUsername()).thenReturn(author.getUsername());
        when(userRepository.findByUsername(archVizArenaUserDetails.getUsername())).thenReturn(Optional.of(author));
        when(projectRepository.save(testProject)).thenReturn(testProject);
        when(modelMapper.map(testProject, ProjectDetailsViewModel.class)).thenReturn(projectWithIncreasedLikes);

        ProjectDetailsViewModel result = projectServiceToTest.likeTheProject(testProject.getId(), archVizArenaUserDetails);

        assertEquals(testProject.getLikesCount(),result.getLikesCount());

        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
     void testProjectReportViewModel(){


        PortfolioProjectEntity testProject=createProject();

        UserEntity author= createArtistEntity();
        List<PictureEntity> projectPictures= List.of(createPicture());
        testProject.setPictures(projectPictures);
        testProject.setAuthor(author);

        when(projectRepository.findById(testProject.getId())).thenReturn(Optional.of(testProject));

        ProjectReportViewModel projectToBeReported = projectServiceToTest.getProjectToBeReported(testProject.getId());

        assertEquals(testProject.getTitle(),projectToBeReported.getTitle());
        assertEquals(testProject.getId(),projectToBeReported.getId());
        assertEquals(testProject.getAuthor().getName(),projectToBeReported.getAuthorName());
        assertEquals(testProject.getAuthor().getId(),projectToBeReported.getAuthorId());



    }
}