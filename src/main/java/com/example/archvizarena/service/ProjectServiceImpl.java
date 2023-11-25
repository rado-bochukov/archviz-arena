package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.CommentViewModel;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.util.mapper.ProjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;
    private final CommentService commentService;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ModelMapper modelMapper, PictureRepository pictureRepository, CommentService commentService,  ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
        this.commentService = commentService;
        this.projectMapper = projectMapper;
    }

    @Override
    public void saveProject(PortfolioProjectServiceModel portfolioProjectToBeSaved, ArchVizArenaUserDetails userDetails) {

        PortfolioProjectEntity project = this.fromPortfolioServiceModel(portfolioProjectToBeSaved, userDetails);
        projectRepository.save(project);

    }

    @Override
    public List<ProjectBrowsingViewModel> findAll() {

        return projectRepository.findAll().stream()
                .map(projectMapper::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDetailsViewModel findById(Long id, ArchVizArenaUserDetails userDetails) {
        PortfolioProjectEntity project = projectRepository.findById(id).orElseThrow();

        return this.mapToDetailsViewModel(project, userDetails); 
    }

    @Override
    public ProjectDetailsViewModel likeTheProject(Long id, ArchVizArenaUserDetails userDetails) {

        PortfolioProjectEntity project = projectRepository.findById(id).orElseThrow();
        project.setLikesCount(project.getLikesCount()+1);
        project.getUsersLikedTheProject().add(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
        projectRepository.save(project);

        PortfolioProjectEntity updatedProject = projectRepository.findById(id).orElseThrow();
        ProjectDetailsViewModel projectToView = this.mapToDetailsViewModel(updatedProject, userDetails);
        projectToView.setLikedFromCurrentUser(true);
        return projectToView;
    }

    private ProjectDetailsViewModel mapToDetailsViewModel(PortfolioProjectEntity project, ArchVizArenaUserDetails userDetails) {
        ProjectDetailsViewModel projectToView = modelMapper.map(project, ProjectDetailsViewModel.class);
        List<String> picturesUrls = project.getPictures().stream()
                .map(PictureEntity::getUrl)
                .toList();
        projectToView.setImagesUrls(picturesUrls);
        projectToView.setAuthorName(project.getAuthor().getName());

        if (userDetails == null) {
            projectToView.setLikedFromCurrentUser(false);
        } else {
            List<String> usernamesLikedTheProject = project.getUsersLikedTheProject()
                    .stream()
                    .map(UserEntity::getUsername)
                    .toList();

            projectToView.setLikedFromCurrentUser(usernamesLikedTheProject.contains(userDetails.getUsername()));
        }

        List<CommentViewModel> comments = project.getComments().stream()
                .map(commentService::mapToCommentViewModel)
                .toList();
        projectToView.setProjectComments(comments);

        return projectToView;
    }


    private PortfolioProjectEntity fromPortfolioServiceModel(PortfolioProjectServiceModel portfolioProjectToBeSaved, ArchVizArenaUserDetails userDetails) {

        PortfolioProjectEntity project = modelMapper.map(portfolioProjectToBeSaved, PortfolioProjectEntity.class);
        UserEntity author = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<PictureEntity> projectPictures = portfolioProjectToBeSaved.getPicturesUrl().stream()
                .map(pictureRepository::findByUrl)
                .toList();

        project.setPublishedOn(LocalDateTime.now());
        project.setLikesCount(0);
        project.setPictures(projectPictures);
        project.setAuthor(author);

        return project;
    }
}
