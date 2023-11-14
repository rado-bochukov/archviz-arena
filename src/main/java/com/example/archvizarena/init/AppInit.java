package com.example.archvizarena.init;

import com.example.archvizarena.service.PictureService;
import com.example.archvizarena.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final PictureService pictureService;

    public AppInit(UserService userService, PictureService pictureService) {
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.init();
        pictureService.initPictures();


    }
}
