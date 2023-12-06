package com.example.archvizarena.service;

import com.example.archvizarena.model.binding.ProjectSearchBindingModel;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    void saveProject(PortfolioProjectServiceModel map, ArchVizArenaUserDetails userDetails);

//    List<ProjectBrowsingViewModel> findAll();
    Page<ProjectBrowsingViewModel> findAllActiveProjects(Pageable pageable);

    ProjectDetailsViewModel findById(Long id, ArchVizArenaUserDetails userDetails);

    ProjectDetailsViewModel likeTheProject(Long id, ArchVizArenaUserDetails userDetails);

    ProjectReportViewModel getProjectToBeReported(Long projectId);

    void deleteProject(Long id);

    Page<ProjectBrowsingViewModel> searchProjects(ProjectSearchBindingModel projectSearchBindingModel, Pageable pageable);

//    void deactivateUserProjects(Long id);

}
