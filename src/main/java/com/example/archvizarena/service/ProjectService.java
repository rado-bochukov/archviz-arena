package com.example.archvizarena.service;

import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;

import java.util.List;

public interface ProjectService {
    void saveProject(PortfolioProjectServiceModel map, ArchVizArenaUserDetails userDetails);

    List<ProjectBrowsingViewModel> findAll();

    ProjectDetailsViewModel findById(Long id, ArchVizArenaUserDetails userDetails);

    ProjectDetailsViewModel likeTheProject(Long id, ArchVizArenaUserDetails userDetails);

}
