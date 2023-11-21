package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.UserRoleEntity;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.view.ArtistViewModel;
import com.example.archvizarena.model.view.CurrentApplicantViewModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.UserRoleRepository;
import com.example.archvizarena.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
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
                .map(artist->{
                    ArtistViewModel artistViewModel= modelMapper.map(artist, ArtistViewModel.class);
                    if(artist.getProfilePicture()!=null){
                    String profilePicUrl=artist.getProfilePicture().getUrl();
                    artistViewModel.setPictureUrl(profilePicUrl);
                    }
                    return artistViewModel;
                })
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
