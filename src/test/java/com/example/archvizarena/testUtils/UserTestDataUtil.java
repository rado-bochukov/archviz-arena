package com.example.archvizarena.testUtils;

import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.enums.CreatorTypeEnum;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public   UserEntity createArtist(String username) {

        var roleEntities= userRoleRepository.findAllByRoleIn(List.of(UserRoleEnum.USER));
        UserEntity artist = new UserEntity();
        artist.setName("Artist 1");
        artist.setUsername(username);
        artist.setEmail("artist1@artists.com");
        artist.setPassword("artist1");
        artist.setCountry("Bulgaria");
        artist.setUserOccupation(UserOccupationEnum.ARTIST);
        artist.setCreatorType(CreatorTypeEnum.FREELANCER);
        artist.setDescription("I am artist 1");
        artist.setPricePerImage(BigDecimal.valueOf(350));
        artist.setRoles(roleEntities);
        return userRepository.save(artist);
    }

    public   UserEntity createTestBuyer(String username) {
        var roleEntities= userRoleRepository.findAllByRoleIn(List.of(UserRoleEnum.USER));
        UserEntity buyer = new UserEntity();
        buyer.setName("Buyer 1");
        buyer.setUsername(username);
        buyer.setEmail("buyer1@buyers.com");
        buyer.setPassword("buyer1");
        buyer.setCountry("France");
        buyer.setUserOccupation(UserOccupationEnum.BUYER);
        buyer.setDescription("I am buyer 1");
        buyer.setRoles(roleEntities);

        return userRepository.save(buyer);
    }

    public   UserEntity createTestAdmin(String username) {
        var roleEntities= userRoleRepository.findAllByRoleIn(List.of(UserRoleEnum.ADMIN));
        UserEntity admin = new UserEntity();
        admin.setName("Admin");
        admin.setUsername(username);
        admin.setEmail("admin@admin.com");
        admin.setPassword("admin");
        admin.setCountry("France");
        admin.setDescription("I am admin");
        admin.setRoles(roleEntities);

        return userRepository.save(admin);
    }

    public void cleanUp(){
        userRepository.deleteAll();
    }
}
