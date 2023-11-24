package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;

public interface ProjectMapper {
    ProjectBrowsingViewModel mapToViewModel(PortfolioProjectEntity portfolioProjectEntity);
}
