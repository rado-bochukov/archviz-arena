package com.example.archvizarena.web;

import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.UserProfileViewModel;
import com.example.archvizarena.model.view.BuyerProfileViewModel;
import com.example.archvizarena.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/artists/details/{id}")
    public String getArtistDetails(@PathVariable Long id,
                                   Model model,
                                   @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        if(userDetails!=null){
            Long principalId= userService.getPrincipalId(userDetails.getUsername());
            model.addAttribute("principalId",principalId);
        }

        UserProfileViewModel artist=userService.findUserById(id);
        model.addAttribute("user",artist);

        return "artist-profile";
    }

    @GetMapping("/buyers/details/{id}")
    public String getBuyerDetails(@PathVariable Long id,
                                   Model model,
                                  @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        if(userDetails!=null){
            Long principalId= userService.getPrincipalId(userDetails.getUsername());
            model.addAttribute("principalId",principalId);
        }


        UserProfileViewModel buyer=userService.findUserById(id);
        model.addAttribute("user",buyer);

        return "buyer-profile";
    }

    @GetMapping("/myProfile")
    public String getMyProfile(Model model,
                               @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        Long userId= userService.getPrincipalId(userDetails.getUsername());
        UserProfileViewModel artist = userService.findUserById(userId);
        model.addAttribute("user",artist);

        if(userDetails.isArtist()){
            return "artist-profile";
        }

        return "buyer-profile";

    }
}
