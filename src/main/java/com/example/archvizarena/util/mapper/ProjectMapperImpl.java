package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapperImpl implements ProjectMapper {
    @Override
    public ProjectBrowsingViewModel mapToViewModel(PortfolioProjectEntity portfolioProjectEntity) {
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
}
