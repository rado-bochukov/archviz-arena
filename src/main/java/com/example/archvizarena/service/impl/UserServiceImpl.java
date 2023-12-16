package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.binding.ArtistSearchBindingModel;
import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.UserRoleEntity;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.*;
import com.example.archvizarena.repository.specification.ArtistSpecification;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.UserRoleRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.ArchVizArenaUserDetailService;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.service.WorkInProgressService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import com.example.archvizarena.util.mapper.ProjectMapper;
import com.example.archvizarena.util.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ProjectMapper projectMapper;
    private final JobPublicationMapper jobPublicationMapper;
    private final UserMapper userMapper;
    private final WorkInProgressService workInProgressService;
    private final PictureRepository pictureRepository;


    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, ProjectMapper projectMapper, JobPublicationMapper jobPublicationMapper, UserMapper userMapper, WorkInProgressService workInProgressService, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.projectMapper = projectMapper;
        this.jobPublicationMapper = jobPublicationMapper;
        this.userMapper = userMapper;
        this.workInProgressService = workInProgressService;
        this.pictureRepository = pictureRepository;
    }

    public void init() {

        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);
            userRoleRepository.save(adminRole);

            UserRoleEntity moderatorRole = new UserRoleEntity();
            moderatorRole.setRole(UserRoleEnum.MODERATOR);
            userRoleRepository.save(moderatorRole);

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRole(UserRoleEnum.USER);
            userRoleRepository.save(userRoleEntity);

            initAdmin(List.of(adminRole, moderatorRole));
        }
    }

    @Override
    public void register(UserRegisterServiceModel userRegisterDto) {

        UserEntity newUser = modelMapper.map(userRegisterDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRoleEnum.USER);
        newUser.setRoles(List.of(userRoleEntity));
        userRepository.save(newUser);
    }

    @Override
    public Page<ArtistViewModel> findAllArtists(Pageable pageable) {
        return userRepository.findAllByUserOccupation_Artist(pageable)
                .map(userMapper::mapToArtistViewModel);
//
    }

    @Override
    public Long getPrincipalId(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->new ObjectNotFoundException("There is no such user!"));
        return user.getId();
    }

    @Override
    public UserProfileViewModel findUserById(Long id, ArchVizArenaUserDetails userDetails) {
        UserEntity user = getUser(id);

        UserProfileViewModel userViewModel = this.mapFromEntity(user);
        userViewModel.setViewerIsOwner(isOwner(id, userDetails));
        return userViewModel;
    }

    @Override
    public boolean isViewerTheOwner(Long profileId, ArchVizArenaUserDetails viewer) {
        return isOwner(profileId, viewer);
    }

    @Override
    public UserEditBindingModel findUserById(Long id) {
        return userMapper.mapFromUserEntity(this.getUser(id));
    }

    @Override
    public void editProfile(UserEditBindingModel userEditBindingModel) {

        UserEntity user = getUser(userEditBindingModel.getId());
        user.setName(userEditBindingModel.getName());
        user.setUsername(userEditBindingModel.getUsername());
        user.setDescription(userEditBindingModel.getDescription());
        user.setCountry(userEditBindingModel.getCountry());

        if (userEditBindingModel.getProfilePicture() != null) {
            PictureEntity profilePicture = pictureRepository.findByUrl(userEditBindingModel.getProfilePicture());
            user.setProfilePicture(profilePicture);
        }
        if (user.getUserOccupation().equals(UserOccupationEnum.ARTIST)) {
            user.setCreatorType(userEditBindingModel.getCreatorType());
            user.setPricePerImage(userEditBindingModel.getPricePerImage());
        }
        userRepository.save(user);

    }

    @Override
    public boolean newUserNameIsUnique(Long id, String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            return true;
        }
        if (this.getUser(id).getUsername().equals(username)) {
            return true;
        }
        return false;
    }

    @Override
    public UserProfileViewModel mapFromEntity(UserEntity user) {

        UserProfileViewModel userViewModel = modelMapper.map(user, UserProfileViewModel.class);
        if (user.getProfilePicture() != null) {
            String profilePicUrl = user.getProfilePicture().getUrl();
            userViewModel.setPictureUrl(profilePicUrl);
        }

        if (user.getUserOccupation().equals(UserOccupationEnum.ARTIST)) {
            List<ProjectBrowsingViewModel> artistProjects = user.getProjects().stream()
                    .map(projectMapper::mapFromEntity)
                    .toList();
            userViewModel.setProjects(artistProjects);

            List<WorkInProgressViewModel> artistWorkInProgress = workInProgressService.getAllArtistWorkInProgress(user.getId());
            userViewModel.setWorkInProgress(artistWorkInProgress);
        } else if (user.getUserOccupation().equals(UserOccupationEnum.BUYER)) {
            List<JobPublicationViewModel> buyerActiveJobs = user.getJobPublications().stream()
                    .filter(JobPublicationEntity::isActive)
                    .map(jobPublicationMapper::mapToJobViewModel)
                    .toList();
            userViewModel.setActiveJobPublications(buyerActiveJobs);
            List<JobPublicationViewModel> buyerInactiveJobs = user.getJobPublications().stream()
                    .filter(j -> !j.isActive())
                    .map(jobPublicationMapper::mapToJobViewModel)
                    .toList();
            userViewModel.setInactiveJobPublications(buyerInactiveJobs);
            List<WorkInProgressViewModel> buyerWorkInProgress = workInProgressService.getAllBuyerWorkInProgress(user.getId());
            userViewModel.setWorkInProgress(buyerWorkInProgress);
        }
        return userViewModel;
    }

    @Override
    public void updateThePrincipalAuthenticationToken(String username, ArchVizArenaUserDetails userDetails) {

        ArchVizArenaUserDetailService archVizArenaUserDetailService = new ArchVizArenaUserDetailService(userRepository);
        UserDetails updatedUserDetails = archVizArenaUserDetailService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                updatedUserDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public String getNameById(Long id) {
        return getUser(id).getName();
    }

    @Override
    public boolean isUserMuted(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("The user you are searching fo does not exist!"));
        return user.isMuted();
    }

    @Override
    public
        Page<ArtistViewModel> searchArtists(ArtistSearchBindingModel artistSearchBindingModel,Pageable pageable) {
        return userRepository.findAll(new ArtistSpecification(artistSearchBindingModel),pageable)
                .map(userMapper::mapToArtistViewModel);
    }


    private UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("The user you are searching for does not exist!"));
    }

    private boolean isOwner(Long profileId, ArchVizArenaUserDetails userDetails) {

        UserEntity user = userRepository.findById(profileId).orElse(null);
        if (userDetails == null || user == null) {
            return false;
        }
        return Objects.equals(
                profileId,
                this.getPrincipalId(userDetails.getUsername())
        );
    }


    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity();
        admin.setRoles(roles);
        admin.setName("Admin");
        admin.setUsername("Admin");
        admin.setEmail("admin@mod.com");
        admin.setPassword(passwordEncoder.encode("11111"));

        userRepository.save(admin);
    }

}
