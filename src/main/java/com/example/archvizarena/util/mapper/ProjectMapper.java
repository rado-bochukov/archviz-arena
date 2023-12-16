package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper  {

    public ProjectBrowsingViewModel mapFromEntity(PortfolioProjectEntity portfolioProjectEntity) {
        if(portfolioProjectEntity==null){
            return null;
        }
        ProjectBrowsingViewModel projectBrowsingViewModel = new ProjectBrowsingViewModel();
        projectBrowsingViewModel.setId(portfolioProjectEntity.getId());
        projectBrowsingViewModel.setCategory(portfolioProjectEntity.getCategory());
        projectBrowsingViewModel.setTitle(portfolioProjectEntity.getTitle());
        projectBrowsingViewModel.setLikesCount(portfolioProjectEntity.getLikesCount());
        projectBrowsingViewModel.setAuthorName(portfolioProjectEntity.getAuthor().getName());
        projectBrowsingViewModel.setPricePerImage(portfolioProjectEntity.getAuthor().getPricePerImage());
        projectBrowsingViewModel.setImagesUrls(portfolioProjectEntity.getPictures().stream()
                .map(PictureEntity::getUrl)
                .collect(Collectors.toList()));
        return projectBrowsingViewModel;
    }

    public ProjectReportViewModel mapFromEntityToReportView(PortfolioProjectEntity project) {
        ProjectReportViewModel projectReportViewModel=new ProjectReportViewModel();
        projectReportViewModel.setId(project.getId());
        projectReportViewModel.setTitle(project.getTitle());
        projectReportViewModel.setAuthorId(project.getAuthor().getId());
        projectReportViewModel.setAuthorName(project.getAuthor().getName());

        return projectReportViewModel;
    }

    public PortfolioProjectEntity mapFromServiceModel(PortfolioProjectServiceModel projectToBeAdded){
        PortfolioProjectEntity project=new PortfolioProjectEntity();

        project.setTitle(projectToBeAdded.getTitle());
        project.setDescription(projectToBeAdded.getDescription());
        project.setCategory(projectToBeAdded.getCategory());

        return project;
    }

    public ProjectDetailsViewModel mapFromEntityToDetailsView(PortfolioProjectEntity portfolioProjectEntity) {
        if(portfolioProjectEntity==null){
            return null;
        }
        ProjectDetailsViewModel projectDetailsViewModel = new ProjectDetailsViewModel();
        projectDetailsViewModel.setCategory(portfolioProjectEntity.getCategory());
        projectDetailsViewModel.setId(portfolioProjectEntity.getId());
        projectDetailsViewModel.setDescription(portfolioProjectEntity.getDescription());
        projectDetailsViewModel.setTitle(portfolioProjectEntity.getTitle());
        projectDetailsViewModel.setLikesCount(portfolioProjectEntity.getLikesCount());
        projectDetailsViewModel.setAuthorName(portfolioProjectEntity.getAuthor().getName());
        projectDetailsViewModel.setPricePerImage(portfolioProjectEntity.getAuthor().getPricePerImage());
        projectDetailsViewModel.setImagesUrls(portfolioProjectEntity.getPictures().stream()
                .map(PictureEntity::getUrl)
                .collect(Collectors.toList()));
        return projectDetailsViewModel;
    }
}
