package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.PictureBindingModel;
import com.example.archvizarena.model.binding.PortfolioProjectBindingModel;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.PictureUploadViewModel;
import com.example.archvizarena.service.CloudinaryService;
import com.example.archvizarena.service.PictureService;
import com.example.archvizarena.service.ProjectService;
import com.example.archvizarena.util.LinkHolder;
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
    private List<PictureUploadViewModel> pictures;

    private final ModelMapper modelMapper;

    private final LinkHolder linkHolder;

    public ProjectUploadController(CloudinaryService cloudinaryService1, ProjectService projectService, PictureService pictureService, List<PictureUploadViewModel> pictures, ModelMapper modelMapper, LinkHolder linkHolder) {
        this.cloudinaryService = cloudinaryService1;
        this.projectService = projectService;
        this.pictureService = pictureService;
        this.pictures = pictures;
        this.modelMapper = modelMapper;
        this.linkHolder = linkHolder;
    }

    @ModelAttribute
    public List<PictureUploadViewModel> pictures() {
        return new ArrayList<>();
    }

    @ModelAttribute
    public PortfolioProjectBindingModel portfolioProjectBindingModel() {
        return new PortfolioProjectBindingModel();
    }


    @GetMapping("/add")
    public String getProjectUpload(Model model) {
        if (pictures == null) {
            pictures = new ArrayList<>();
        }
        model.addAttribute("uploadedPictures", pictures);
        model.addAttribute("links",linkHolder);

        return "project-add";
    }

    @PostMapping("/add/upload-images")
    public String uploadProject(@RequestParam("file") MultipartFile file,
                                @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {
        try {
            // Upload the image to Cloudinary
            String imageUrl = cloudinaryService.uploadFile(file);

            // Create a new PictureBindingModel object and set the image URL
            linkHolder.getImagesLink().add(imageUrl);
            pictures.add(new PictureUploadViewModel(file.getOriginalFilename()));

            pictureService.savePicture(imageUrl);
//            pictureService.savePicture(imageUrl,userDetails);

            return "redirect:/projects/add"; // Redirect to the create project page to continue uploading

        } catch (Exception e) {
            // Handle the exception, e.g., show an error page
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


        portfolioProjectBindingModel.setPicturesUrl(linkHolder.getImagesLink());

        projectService.saveProject(modelMapper.map(portfolioProjectBindingModel, PortfolioProjectServiceModel.class),userDetails);

        pictures.clear();
        linkHolder.clear();

        return "redirect:/";
    }
}
