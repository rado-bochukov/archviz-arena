package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.view.ArtistViewModel;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
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
    @Mock
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
                modelMapper,
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

    }

    @Test
    public void testRegisterSuccessful(){

        UserRegisterServiceModel userRegisterDto = new UserRegisterServiceModel();

        UserEntity newUser = new UserEntity();
        when(modelMapper.map(userRegisterDto, UserEntity.class)).thenReturn(newUser);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(mockUserRoleRepository.findByRole(UserRoleEnum.USER)).thenReturn(new UserRoleEntity());


        userServiceToTest.register(userRegisterDto);


        verify(modelMapper, times(1)).map(userRegisterDto, UserEntity.class);
        verify(passwordEncoder, times(1)).encode(userRegisterDto.getPassword());
        verify(mockUserRoleRepository, times(1)).findByRole(UserRoleEnum.USER);
        verify(mockUserRepository, times(1)).save(newUser);
    }


@Test
    public void testFindAllArtists(){
    Pageable pageable=Pageable.ofSize(2);
    UserEntity artist1=createArtistEntity();
    UserEntity artist2=createArtistEntity();
    artist2.setId(2L);
    List<UserEntity> artists=List.of(artist1,artist2);

    when(mockUserRepository.findAllByUserOccupation_Artist(pageable))
            .thenReturn(new PageImpl<>(artists));

    Page<ArtistViewModel> result = userServiceToTest.findAllArtists(pageable);

    verify(mockUserRepository, times(1)).findAllByUserOccupation_Artist(pageable);

    Assertions.assertEquals(artists.size(), result.getContent().size());
}

@Test
    public void testGetPrincipalIdShouldReturn_1(){
        UserEntity artist=createArtistEntity();
    when(mockUserRepository.findByUsername(artist.getUsername()))
            .thenReturn(Optional.of(artist));

    Long principalId = userServiceToTest.getPrincipalId(artist.getUsername());

    Assertions.assertEquals(artist.getId(),principalId);

}
    @Test
    public void testGetPrincipalIdShouldFail(){

        assertThrows(ObjectNotFoundException.class,
                () -> userServiceToTest.getPrincipalId("test"));

    }

    @Test
   public void TestEditProfile(){

        UserEditBindingModel userEditBindingModel = editArtistEntity();
        PictureEntity profilePicture=createProfilePicture();
        userEditBindingModel.setProfilePicture(profilePicture.getUrl());
        UserEntity artist = createArtistEntity();
        when(mockUserRepository.findById(artist.getId())).thenReturn(Optional.of(artist));
        when(mockPictureRepository.findByUrl(profilePicture.getUrl())).thenReturn(profilePicture);


        userServiceToTest.editProfile(userEditBindingModel);


        verify(mockUserRepository, times(1)).findById(any(Long.class));
        verify(mockPictureRepository, times(1)).findByUrl(any(String.class));
        verify(mockUserRepository, times(1)).save(artist);
    }

    @Test
    public void  testNewUserNameIsUnique_ReturnTrue(){
        UserEntity artist=createArtistEntity();
        String newUsername="newUsername";


        userServiceToTest.newUserNameIsUnique(artist.getId(),newUsername);

        Assertions.assertTrue(userServiceToTest.newUserNameIsUnique(artist.getId(),newUsername));
    }

    @Test
    public void  testNewUserNameIsUnique_ReturnTrueWhenNewUsernameIsTheSame(){
        UserEntity artist=createArtistEntity();
        String newUsername=artist.getUsername();
        when(mockUserRepository.findByUsername(artist.getUsername())).thenReturn(Optional.of(artist));
        when(mockUserRepository.findById(artist.getId())).thenReturn(Optional.of(artist));

        Assertions.assertTrue(userServiceToTest.newUserNameIsUnique(artist.getId(),newUsername));
    }

    @Test
    public void  testGetNameById(){
        UserEntity artist=createArtistEntity();
        when(mockUserRepository.findById(artist.getId())).thenReturn(Optional.of(artist));

        String nameById = userServiceToTest.getNameById(artist.getId());

        Assertions.assertEquals(artist.getName(),nameById);
    }

    @Test
    public void  testIsUserMuted_ReturnTrue(){
        UserEntity artist=createArtistEntity();
        artist.setMuted(true);
        when(mockUserRepository.findByUsername(artist.getUsername())).thenReturn(Optional.of(artist));

        boolean userMuted = userServiceToTest.isUserMuted(artist.getUsername());

        Assertions.assertTrue(userMuted);
    }

    @Test
    public void  testIsUserMuted_ReturnFalse(){
        UserEntity artist=createArtistEntity();
        artist.setMuted(false);
        when(mockUserRepository.findByUsername(artist.getUsername())).thenReturn(Optional.of(artist));

        boolean userMuted = userServiceToTest.isUserMuted(artist.getUsername());

        Assertions.assertFalse(userMuted);
    }










}