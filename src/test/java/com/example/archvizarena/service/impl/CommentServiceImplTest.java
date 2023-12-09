package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.CommentEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.CommentAddServiceModel;
import com.example.archvizarena.model.view.CommentViewModel;
import com.example.archvizarena.repository.CommentRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.CommentService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private CommentService commentServiceToTest;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;

    private CommentEntity testComment;

    private UserEntity commentAuthor;

    private PortfolioProjectEntity project;

    @BeforeEach
    void setUp(){
        commentServiceToTest=new CommentServiceImpl(
                commentRepository,
                projectRepository,
                userRepository
        );

        project= createProject();
        commentAuthor=createArtistEntity();
        testComment=createComment();
    }

    @Test
    void testCommentNotFound(){
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> commentServiceToTest.findById(2L));
    }

    @Test
    void testFindCommentById_returnComment(){
        testComment.setProject(project);
        testComment.setCommentAuthor(commentAuthor);
        testComment.setCreated(LocalDateTime.now());

        when(commentRepository.findById(testComment.getId())).thenReturn(Optional.of(testComment));

        CommentViewModel foundComment = commentServiceToTest.findById(testComment.getId());

        Assertions.assertEquals(testComment.getId(),foundComment.getId());
        Assertions.assertEquals(testComment.getCommentAuthor().getName(),foundComment.getAuthorName());
        Assertions.assertEquals(testComment.getTextContent(),foundComment.getTextContent());
    }


    @Test
    void testSaveAndAddComment(){
        CommentAddServiceModel commentToBeAdded=createCommentServiceModel();
        commentToBeAdded.setUsername(commentAuthor.getUsername());
        commentToBeAdded.setProjectId(project.getId());
        testComment.setCommentAuthor(commentAuthor);
        testComment.setCreated(LocalDateTime.now());

        when(userRepository.findByUsername(commentToBeAdded.getUsername())).thenReturn(Optional.of(commentAuthor));
        when(projectRepository.findById(commentToBeAdded.getProjectId())).thenReturn(Optional.of(project));

       commentServiceToTest.saveAndAddComment(commentToBeAdded);

        verify(userRepository, times(1)).findByUsername(commentAuthor.getUsername());
        verify(projectRepository, times(1)).findById(project.getId());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));

    }



}