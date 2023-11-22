package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {


    ProjectBrowsingViewModel mapToViewModel(PortfolioProjectEntity portfolioProjectEntity);
}
