package com.example.archvizarena.web;

import com.example.archvizarena.model.view.ArtistProfileViewModel;
import com.example.archvizarena.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/artists/details/{id}")
    public String getArtistDetails(@PathVariable Long id,
                                   Model model){

        ArtistProfileViewModel artist=userService.findUserById(id);
        model.addAttribute("artist",artist);

        return "artist-profile";
    }
}
