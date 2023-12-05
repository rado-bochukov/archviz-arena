package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.CommentAddBindingModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.service.ProjectService;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectViewController {

    private final ProjectService projectService;

    public ProjectViewController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public String getBrowseProjects(Model model,
                                   @PageableDefault(page = 0,
                                   size = 5) Pageable pageable) {

        Page<ProjectBrowsingViewModel> allProjects = projectService.findAllActiveProjects(pageable);
        long count = allProjects.getTotalElements();
        model.addAttribute("allProjects", allProjects);
        model.addAttribute("projectsCount", count);

        return "projects-browse";
    }

    @ModelAttribute(name = "commentToBeAdded")
    public CommentAddBindingModel commentAddBindingModel() {
        return new CommentAddBindingModel();
    }

    @GetMapping("/details/{id}")
    public String getProjectDetails(@PathVariable Long id,
                                    Model model,
                                    @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        ProjectDetailsViewModel project = projectService.findById(id, userDetails);
        model.addAttribute("project", project);

        return "project-detail";
    }

    @PostMapping("/details/like/{id}")
    public String likeTheProject(@PathVariable Long id,
                                 @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        projectService.likeTheProject(id, userDetails);
        return "redirect:/projects/details/" + id;
    }

}
