package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.ArtistSearchBindingModel;
import com.example.archvizarena.model.view.ArtistViewModel;
import com.example.archvizarena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final UserService userService;

    public ArtistController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public ArtistSearchBindingModel artistSearchBindingModel(){
        return new ArtistSearchBindingModel();
    }

    @GetMapping("/all")
    public String getArtists(Model model,
                             @PageableDefault(size = 8) Pageable pageable) {

        Page<ArtistViewModel> allArtists = userService.findAllArtists(pageable);
        long foundArtistsCount = allArtists.getTotalElements();

        model.addAttribute("allArtists", allArtists);
        model.addAttribute("countArtists", foundArtistsCount);
        return "3d-artists";
    }

    @GetMapping("/search")
    public String searchArtists(@Valid ArtistSearchBindingModel artistSearchBindingModel,
                                Model model) {


        if (!artistSearchBindingModel.isEmpty()) {
            List<ArtistViewModel> foundArtists = userService.searchArtists(artistSearchBindingModel);
            int foundArtistsCount = foundArtists.size();
            model.addAttribute("allArtists", foundArtists);
            model.addAttribute("countArtists", foundArtistsCount);
        }

        return "3d-artists-search";
    }

}
