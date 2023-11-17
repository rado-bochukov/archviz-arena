package com.example.archvizarena.web;

import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.service.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectViewController {

    private final ProjectService projectService;

    public ProjectViewController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public String getBrowseProjects(Model model){

        List<ProjectBrowsingViewModel>allProjects=projectService.findAll();
        int count=allProjects.size();
        model.addAttribute("allProjects",allProjects);
        model.addAttribute("projectsCount",count);

      return "projects-browse";
    }

    @GetMapping("/details/{id}")
    public String getProjectDetails(@PathVariable Long id,
                                    Model model,
                                    @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        ProjectDetailsViewModel project=projectService.findById(id,userDetails);

        model.addAttribute("project",project);


        return "project-detail";
    }

}
