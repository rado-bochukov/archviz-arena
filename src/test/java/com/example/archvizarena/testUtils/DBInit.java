package com.example.archvizarena.testUtils;

import java.util.List;

import com.example.archvizarena.model.entity.UserRoleEntity;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {

      UserRoleEntity user=  new UserRoleEntity();
              user.setRole(UserRoleEnum.USER);
        UserRoleEntity admin=  new UserRoleEntity();
        admin.setRole(UserRoleEnum.ADMIN);

        if (userRoleRepository.count() == 0) {
            userRoleRepository.saveAll(List.of(
                    user,admin
            ));
        }
    }
}