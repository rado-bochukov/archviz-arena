package com.example.archvizarena.web;

import com.example.archvizarena.model.view.ArtistViewModel;
import com.example.archvizarena.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final UserService userService;

    public ArtistController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getArtists(Model model){

        List<ArtistViewModel> allArtists =userService.findAllArtists();
        int foundArtistsCount=allArtists.size();

        model.addAttribute("allArtists",allArtists);
        model.addAttribute("countArtists",foundArtistsCount);
        return "3d-artists";
    }

}
