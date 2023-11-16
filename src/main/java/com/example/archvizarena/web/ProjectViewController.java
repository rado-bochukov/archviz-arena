package com.example.archvizarena.web;

import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


      return "projects-browse";
    }

}
