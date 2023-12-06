package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.view.UserProfileViewModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.UserRoleRepository;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.service.WorkInProgressService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.util.mapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userServiceToTest;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserRoleRepository mockUserRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private ProjectMapper projectMapper;
    @Mock
    private JobPublicationMapper jobPublicationMapper;
    private UserMapper userMapper;
    @Mock
    private WorkInProgressService workInProgressService;
    @Mock
    private PictureRepository mockPictureRepository;

    @BeforeEach
    void setUp() {
        userServiceToTest = new UserServiceImpl(mockUserRepository,
                mockUserRoleRepository,
                passwordEncoder,
                new ModelMapper(),
                new ProjectMapper(),
                jobPublicationMapper,
                new UserMapper(new ModelMapper()),
                workInProgressService,
                mockPictureRepository);
    }

    @Test
    void testUserNotFound() {
        assertThrows(ObjectNotFoundException.class,
                () -> userServiceToTest.findUserById(2L));
    }

    @Test
    void testUserProfileViewModel() {

//        arrange
        PictureEntity picture = createPicture();
        PictureEntity profilePicture = createProfilePicture();
        PortfolioProjectEntity project = createProject();
        UserEntity testArtist = createArtistEntity();
        project.setAuthor(testArtist);
        project.setPictures(List.of(picture));
        testArtist.setProjects(List.of(project));
        testArtist.setProfilePicture(profilePicture);
        UserProfileViewModel referenceArtist = testArtistProfile();
        when(mockUserRepository.findById(testArtist.getId()))
                .thenReturn(Optional.of(testArtist));

//        act
        UserProfileViewModel artistViewProfile = userServiceToTest.findUserById(testArtist.getId(), null);

//        assert
        Assertions.assertEquals(referenceArtist.getUserOccupation(), artistViewProfile.getUserOccupation());
        Assertions.assertEquals(referenceArtist.getCountry(), artistViewProfile.getCountry());

    }

    @Test
    void testUserProfileViewModel_Buyer() {

//        arrange
        PictureEntity profilePicture = createProfilePicture();
        JobPublicationEntity job = createJobPublication();
        UserEntity testBuyer = createBuyerEntity();
        testBuyer.setJobPublications(List.of(job));
        testBuyer.setProfilePicture(profilePicture);
        UserProfileViewModel referenceArtist = testBuyerProfile();
        when(mockUserRepository.findById(testBuyer.getId()))
                .thenReturn(Optional.of(testBuyer));

//        act
        UserProfileViewModel artistViewProfile = userServiceToTest.findUserById(testBuyer.getId(), null);

//        assert
        Assertions.assertEquals(referenceArtist.getUserOccupation(), artistViewProfile.getUserOccupation());
        Assertions.assertEquals(referenceArtist.getCountry(), artistViewProfile.getCountry());

    }

    @Test
    public void testInit() {
        // Arrange
        when(mockUserRepository.count()).thenReturn(0L);
        when(mockUserRoleRepository.count()).thenReturn(0L);

        // Act
        userServiceToTest.init();

        // Assert
        verify(mockUserRoleRepository, times(3)).save(any(UserRoleEntity.class));
        verify(mockUserRepository, times(1)).save(any(UserEntity.class));

        // You may also want to verify that the initAdmin method was called with the expected roles
        // For simplicity, let's assume initAdmin is a method of yourClass

    }


    private static UserProfileViewModel testArtistProfile() {
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

    private static UserProfileViewModel testBuyerProfile() {
        UserProfileViewModel buyerProfileViewModel = new UserProfileViewModel();
        buyerProfileViewModel.setId(2L);
        buyerProfileViewModel.setName("Buyer 1");
        buyerProfileViewModel.setCountry("France");
        buyerProfileViewModel.setUserOccupation(UserOccupationEnum.BUYER);
        buyerProfileViewModel.setDescription("I am buyer 1");
        buyerProfileViewModel.setViewerIsOwner(false);
        return buyerProfileViewModel;

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

    private static UserEntity createBuyerEntity() {
        UserEntity buyer = new UserEntity();
        buyer.setId(2L);
        buyer.setName("Buyer 1");
        buyer.setCountry("France");
        buyer.setUserOccupation(UserOccupationEnum.BUYER);
        buyer.setDescription("I am buyer 1");

        return buyer;
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

    private static JobPublicationEntity createJobPublication() {

        JobPublicationEntity job = new JobPublicationEntity();
        job.setTitle("Job_p 1");
        job.setDescription("job 1 description");
        job.setActive(true);
        job.setCategory(ProjectCategoryEnum.EXTERIOR);

        return job;
    }

    private static PictureEntity createPicture() {
        PictureEntity picture = new PictureEntity();

        picture.setUrl("pictureUrl");
        picture.setId(1L);
        return picture;
    }

    private static PictureEntity createProfilePicture() {
        PictureEntity picture = new PictureEntity();

        picture.setUrl("ProfilePictureUrl");
        picture.setId(2L);
        return picture;
    }

}