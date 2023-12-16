package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.PortfolioProjectBindingModel;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.CloudinaryService;
import com.example.archvizarena.service.PictureService;
import com.example.archvizarena.service.ProjectService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectUploadController {

    private final CloudinaryService cloudinaryService;
    private final ProjectService projectService;
    private final PictureService pictureService;
    private List<String> imageLinks=new ArrayList<>();
    private final ModelMapper modelMapper;

    public ProjectUploadController(CloudinaryService cloudinaryService1, ProjectService projectService, PictureService pictureService,  ModelMapper modelMapper) {
        this.cloudinaryService = cloudinaryService1;
        this.projectService = projectService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }


    @ModelAttribute
    public PortfolioProjectBindingModel portfolioProjectBindingModel() {
        return new PortfolioProjectBindingModel();
    }


    @GetMapping("/add")
    public String getProjectUpload(Model model) {

        model.addAttribute("links",imageLinks);

        return "project-add";
    }

    @PostMapping("/add/upload-images")
    public String uploadProject(@RequestParam("file") MultipartFile file) {
        try {
            // Upload the image to Cloudinary
            String imageUrl = cloudinaryService.uploadFile(file);

            imageLinks.add(imageUrl);
            pictureService.savePicture(imageUrl);
            return "redirect:/projects/add";

        } catch (Exception e) {
            return "redirect:/projects/add";
        }
    }

    @PostMapping("/add")
    public String publishProject(@Valid PortfolioProjectBindingModel portfolioProjectBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("portfolioProjectBindingModel", portfolioProjectBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.portfolioProjectBindingModel", bindingResult);

            return "redirect:/projects/add";
        }

        portfolioProjectBindingModel.setPicturesUrl(imageLinks);
        projectService.saveProject(modelMapper.map(portfolioProjectBindingModel,
                PortfolioProjectServiceModel.class),userDetails.getUsername());
        imageLinks.clear();

        return "redirect:/";
    }
}
