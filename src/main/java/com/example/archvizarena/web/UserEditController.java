package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.CloudinaryService;
import com.example.archvizarena.service.PictureService;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.util.LinkHolder;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserEditController {

    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    private List<String> profileImageLink  ;
    private final PictureService pictureService;

    public UserEditController(UserService userService, CloudinaryService cloudinaryService, PictureService pictureService) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.pictureService = pictureService;
    }

    @GetMapping("/edit-profile")
    public String redirectEditProfile(@AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        Long userId = userService.getPrincipalId(userDetails.getUsername());
        return "redirect:/users/edit-profile/" + userId;
    }

    @PreAuthorize("@userServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @GetMapping("/edit-profile/{id}")
    public String getEditProfile(@PathVariable Long id,
                                 Model model,
                                 @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        model.addAttribute("links",profileImageLink);
        model.addAttribute("profileId",id);

        return "edit-user";
    }


    @PostMapping("/edit-profile/{id}/upload-profile-image")
    public String uploadProfilePicture(@PathVariable Long id,
                                       @RequestParam("file") MultipartFile file,
                                       @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {
        try {

            String imageUrl = cloudinaryService.uploadFile(file);
           profileImageLink.add(imageUrl);
            pictureService.savePicture(imageUrl);

            return "redirect:/users/edit-profile/"+id;

        } catch (Exception e) {

            return "redirect:/users/edit-profile/"+id;
        }
    }

    @ModelAttribute(name = "newUserNameIsUnique")
    public Boolean editedUserNameIsUnique(){

        return true;
    }
    @ModelAttribute(name = "userEditBindingModel")
    public UserEditBindingModel userEditBindingModel(@AuthenticationPrincipal ArchVizArenaUserDetails userDetails){
        Long userId = userService.getPrincipalId(userDetails.getUsername());

        return userService.findUserById(userId);
    }
    @PreAuthorize("@userServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @PostMapping("/edit-profile/{id}")
    public String editProfile(@PathVariable Long id,
                              @Valid UserEditBindingModel userEditBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userEditBindingModel",userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel", bindingResult);

            return "redirect:/users/edit-profile/" + id;
        }
        if(!userService.newUserNameIsUnique(id,userEditBindingModel.getUsername())){

            redirectAttributes.addFlashAttribute("userEditBindingModel",userEditBindingModel);
            redirectAttributes.addFlashAttribute("newUserNameIsUnique",false);
            return "redirect:/users/edit-profile/" + id;
        }

        if(!profileImageLink.isEmpty()){
            userEditBindingModel.setProfilePicture(profileImageLink.get(0));
        }

        userService.editProfile(userEditBindingModel);
        userService.updateThePrincipalAuthenticationToken(userEditBindingModel.getUsername(),userDetails);
        profileImageLink.clear();

        return String.format("redirect:/users/edit-profile/%d/success",id);
    }

    @PreAuthorize("@userServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @GetMapping("/edit-profile/{id}/success")
    public String getEditProfile(@PathVariable Long id,
                                 @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        return "edit-profile-success";
    }
}
