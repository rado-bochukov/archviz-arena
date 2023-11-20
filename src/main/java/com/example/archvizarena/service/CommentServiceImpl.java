package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.CommentEntity;
import com.example.archvizarena.model.service.CommentAddServiceModel;
import com.example.archvizarena.model.view.CommentViewModel;
import com.example.archvizarena.repository.CommentRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CommentViewModel mapToCommentViewModel(CommentEntity comment) {

        CommentViewModel commentViewModel=new CommentViewModel();
        commentViewModel.setAuthorName(comment.getCommentAuthor().getName());
        commentViewModel.setCreated(comment.getCreated());
        commentViewModel.setTextContent(comment.getTextContent());
        return commentViewModel;
    }

    @Override
    public void saveAndAddComment(CommentAddServiceModel commentToBeAdded) {
        CommentEntity comment=new CommentEntity();
        comment.setCommentAuthor(userRepository.findByUsername(commentToBeAdded.getUsername()).get());
        comment.setTextContent(commentToBeAdded.getTextContent());
        comment.setProject(projectRepository.findById(commentToBeAdded.getProjectId()).get());
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
