package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.model.service.PortfolioProjectServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.CommentViewModel;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.util.mapper.ProjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ModelMapper modelMapper, PictureRepository pictureRepository, CommentService commentService, ProjectMapper projectMapper) {
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
    public Page<ProjectBrowsingViewModel> findAllActiveProjects(Pageable pageable) {

        return projectRepository.findAllByIsActiveTrue(pageable)
                .map(projectMapper::mapFromEntity);

    }

    @Override
    public ProjectDetailsViewModel findById(Long id, ArchVizArenaUserDetails userDetails) {
        PortfolioProjectEntity project = getProject(id);
//        if(!project.isActive()){
//            if(!isViewerTheOwner(id, userDetails)){
//                throw new ObjectNotFoundException("Oops , seems the publication you are looking for cannot be reached!");
//            }
//        }

        return this.mapToDetailsViewModel(project, userDetails);
    }

    private PortfolioProjectEntity getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("The project you are looking for does not exist! Maybe you should create it :)"));
    }

    @Override
    public ProjectDetailsViewModel likeTheProject(Long id, ArchVizArenaUserDetails userDetails) {

        PortfolioProjectEntity project = getProject(id);
//        if(!project.isActive()){
//            if(!isViewerTheOwner(id, userDetails)){
//                throw new ObjectNotFoundException("Oops , seems the publication you are looking for cannot be reached!");
//            }
//        }
        project.setLikesCount(project.getLikesCount() + 1);
        project.getUsersLikedTheProject().add(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
        projectRepository.save(project);

        PortfolioProjectEntity updatedProject = getProject(id);
        ProjectDetailsViewModel projectToView = this.mapToDetailsViewModel(updatedProject, userDetails);
        projectToView.setLikedFromCurrentUser(true);
        return projectToView;
    }

    @Override
    public ProjectReportViewModel getProjectToBeReported(Long projectId) {
        PortfolioProjectEntity project = this.getProject(projectId);
//        if(!project.isActive()){
//            throw new ObjectNotFoundException("Oops , seems the publication you are looking for cannot be reached!");
//        }
        return projectMapper.mapFromEntityToReportView(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

//    @Override
//    @Transactional
//    public void deactivateUserProjects(Long id) {
//        projectRepository.findAllByAuthor_Id(id)
//                .forEach(project->{
//                    project.setActive(false);
//                    projectRepository.save(project);
//                });
//    }

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
        projectToView.setViewerTheOwner(this.isViewerTheOwner(project.getAuthor().getId(), userDetails));

        return projectToView;
    }

    private boolean isViewerTheOwner(Long id, ArchVizArenaUserDetails userDetails) {
        return isOwner(id, userDetails, userRepository);
    }

    static boolean isOwner(Long id, ArchVizArenaUserDetails userDetails, UserRepository userRepository) {
        if(userDetails == null){
            return false;
        }

        UserEntity user= userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if(user == null){
            return false;
        }

        return user.getJobPublications().stream().map(BaseEntity::getId)
                .toList().contains(id);
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
        project.setActive(true);

        return project;
    }
}
