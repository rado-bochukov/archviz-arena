package com.example.archvizarena.web;

import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.UserProfileViewModel;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserProfileController {
    private final UserService userService;


    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details/{id}")
    public String getBuyerDetails(@PathVariable Long id,
                                  Model model,
                                  @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        UserProfileViewModel user = userService.findUserById(id,userDetails);

        model.addAttribute("user", user);
        if (user.getUserOccupation().equals(UserOccupationEnum.ARTIST)) {
            return "artist-profile";
        }
        return "buyer-profile";
    }

    @GetMapping("/myProfile")
    public String getMyProfile(@AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        Long userId = userService.getPrincipalId(userDetails.getUsername());
        return "redirect:/users/details/" + userId;
    }












}
