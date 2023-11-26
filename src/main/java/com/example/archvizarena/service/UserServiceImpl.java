package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.UserRoleEntity;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.*;
import com.example.archvizarena.repository.UserRoleRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import com.example.archvizarena.util.mapper.ProjectMapper;
import com.example.archvizarena.util.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, ProjectMapper projectMapper, JobPublicationMapper jobPublicationMapper, UserMapper userMapper, WorkInProgressService workInProgressService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.projectMapper = projectMapper;
        this.jobPublicationMapper = jobPublicationMapper;
        this.userMapper = userMapper;
        this.workInProgressService = workInProgressService;
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
            initModerator(List.of(moderatorRole));

        }

    }

    @Override
    public void register(UserRegisterServiceModel userRegisterDto) {
        UserEntity newUser = modelMapper.map(userRegisterDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        UserRoleEntity userRoleEntity =userRoleRepository.findByRole(UserRoleEnum.USER);
        newUser.setUserRoles(List.of(userRoleEntity));

        userRepository.save(newUser);

    }

    @Override
    public List<ArtistViewModel> findAllArtists() {
        return userRepository.findAll().stream()
                .filter(u->u.getUserOccupation()!=null)
                .filter(u->u.getUserOccupation().name().equals("ARTIST"))
                .map(userMapper::mapToArtistViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public CurrentApplicantViewModel findCurrentApplicantInfo(String username) {
        CurrentApplicantViewModel applicant=new CurrentApplicantViewModel();
        UserEntity user=userRepository.findByUsername(username).orElseThrow();
        applicant.setName(user.getName());
        applicant.setId(user.getId());
        return applicant;
    }

    @Override
    public Long getPrincipalId(String username) {

        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        return user.getId();
    }

    @Override
    public UserProfileViewModel findUserById(Long id, ArchVizArenaUserDetails userDetails) {
        UserEntity user=userRepository.findById(id).orElseThrow();
        UserProfileViewModel userViewModel= modelMapper.map(user, UserProfileViewModel.class);
        if(user.getProfilePicture()!=null){
            String profilePicUrl=user.getProfilePicture().getUrl();
            userViewModel.setPictureUrl(profilePicUrl);
        }

        if(user.getUserOccupation().equals(UserOccupationEnum.ARTIST)){
        List<ProjectBrowsingViewModel> artistProjects = user.getProjects().stream()
                .map(projectMapper::mapToViewModel)
                .toList();
        userViewModel.setProjects(artistProjects);

        List<WorkInProgressViewModel> artistWorkInProgress=workInProgressService.getAllArtistWorkInProgress(id);
        userViewModel.setWorkInProgress(artistWorkInProgress);
        }else if (user.getUserOccupation().equals(UserOccupationEnum.BUYER)){
            List<JobPublicationViewModel> buyerActiveJobs = user.getJobPublications().stream()
                    .filter(JobPublicationEntity::isActive)
                    .map(jobPublicationMapper::mapToJobViewModel)
                    .toList();
            userViewModel.setActiveJobPublications(buyerActiveJobs);
            List<JobPublicationViewModel> buyerInactiveJobs = user.getJobPublications().stream()
                    .filter(j->!j.isActive())
                    .map(jobPublicationMapper::mapToJobViewModel)
                    .toList();
            userViewModel.setInactiveJobPublications(buyerInactiveJobs);
            List<WorkInProgressViewModel> buyerWorkInProgress=workInProgressService.getAllBuyerWorkInProgress(id);
            userViewModel.setWorkInProgress(buyerWorkInProgress);
        }
        userViewModel.setViewerIsOwner(isOwner(id,userDetails));
        return userViewModel;
    }

    @Override
    public boolean isViewerTheOwner(Long profileId, ArchVizArenaUserDetails viewer) {
        return isOwner(profileId,viewer);
    }

    private boolean isOwner(Long profileId, ArchVizArenaUserDetails userDetails){

        UserEntity user=userRepository.findById(profileId).orElse(null);
        if(userDetails==null||user==null){
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

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity();
        moderator.setRoles(roles);
        moderator.setName("Moderator");
        moderator.setEmail("moderator@mod.com");
        moderator.setUsername("Moderator");
        moderator.setPassword(passwordEncoder.encode("22222"));

        userRepository.save(moderator);

    }
}
