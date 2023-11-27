package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.binding.UserRegisterBindingModel;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.PictureUploadViewModel;
import com.example.archvizarena.model.view.UserProfileViewModel;
import com.example.archvizarena.service.CloudinaryService;
import com.example.archvizarena.service.PictureService;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.util.LinkHolder;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserProfileController {
    private final UserService userService;


    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details/{id}")
    public String getBuyerDetails(@PathVariable Long id,
                                  Model model) {

        UserProfileViewModel user = userService.findUserViewModelById(id);
        // TODO: 26.11.2023 г. error handling for null and admin id

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
