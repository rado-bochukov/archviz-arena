package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PictureRepository pictureRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ModelMapper modelMapper, PictureRepository pictureRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void saveProject(PortfolioProjectServiceModel portfolioProjectToBeSaved, ArchVizArenaUserDetails userDetails) {

        PortfolioProjectEntity project = this.fromPortfolioServiceModel(portfolioProjectToBeSaved, userDetails);
        projectRepository.save(project);

    }

    @Override
    public List<ProjectBrowsingViewModel> findAll() {
        List<PortfolioProjectEntity>result=projectRepository.findAll();
        return projectRepository.findAll().stream().map(p->modelMapper.map(p, ProjectBrowsingViewModel.class))
                .collect(Collectors.toList());
    }

    private PortfolioProjectEntity fromPortfolioServiceModel(PortfolioProjectServiceModel portfolioProjectToBeSaved, ArchVizArenaUserDetails userDetails) {

        PortfolioProjectEntity project = modelMapper.map(portfolioProjectToBeSaved, PortfolioProjectEntity.class);
        UserEntity author = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<PictureEntity> projectPictures = portfolioProjectToBeSaved.getPicturesUrl().stream()
                .map(pictureRepository::findByUrl)
                .toList();

        project.setPublishedOn(LocalDateTime.now());
        project.setLikeCount(0);
        project.setPictures(projectPictures);
        project.setAuthor(author);

        return project;
    }
}
