package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.User;
import com.example.archvizarena.model.entity.UserRole;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.UserRoleRepository;
import com.example.archvizarena.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
            UserRole adminRole = new UserRole();
            adminRole.setRole(UserRoleEnum.ADMIN);
            userRoleRepository.save(adminRole);

            UserRole moderatorRole = new UserRole();
            moderatorRole.setRole(UserRoleEnum.MODERATOR);
            userRoleRepository.save(moderatorRole);

            UserRole userRole = new UserRole();
            userRole.setRole(UserRoleEnum.USER);
            userRoleRepository.save(userRole);


            initAdmin(List.of(adminRole, moderatorRole));
            initModerator(List.of(moderatorRole));

        }

    }

    @Override
    public void register(UserRegisterServiceModel userRegisterDto) {
        User newUser = modelMapper.map(userRegisterDto, User.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        UserRole userRole=userRoleRepository.findByRole(UserRoleEnum.USER);
        newUser.setUserRoles(List.of(userRole));
        newUser.setProfilePicture(pictureRepository.findById(1L).orElse(null));
        userRepository.save(newUser);

    }

    private void initAdmin(List<UserRole> roles) {
        User admin = new User();
        admin.setRoles(roles);
        admin.setName("Admin");
        admin.setUsername("Admin");
        admin.setEmail("admin@mod.com");
        admin.setPassword(passwordEncoder.encode("11111"));

        userRepository.save(admin);

    }

    private void initModerator(List<UserRole> roles) {
        User moderator = new User();
        moderator.setRoles(roles);
        moderator.setName("Moderator");
        moderator.setEmail("moderator@mod.com");
        moderator.setUsername("Moderator");
        moderator.setPassword(passwordEncoder.encode("22222"));

        userRepository.save(moderator);

    }
}
