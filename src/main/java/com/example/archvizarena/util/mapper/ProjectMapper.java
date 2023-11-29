package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;

public interface ProjectMapper {
    ProjectBrowsingViewModel mapFromEntity(PortfolioProjectEntity portfolioProjectEntity);

    ProjectReportViewModel mapFromEntityToReportView(PortfolioProjectEntity project);
}
