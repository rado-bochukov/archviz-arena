package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.CommentEntity;
import com.example.archvizarena.model.service.CommentAddServiceModel;
import com.example.archvizarena.model.view.CommentViewModel;
import com.example.archvizarena.repository.CommentRepository;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.CommentService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        commentViewModel.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        commentViewModel.setTextContent(comment.getTextContent());
        commentViewModel.setId(comment.getId());
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

    @Override
    public CommentViewModel findById(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ObjectNotFoundException("The comment you are looking for does not exist!"));

        return this.mapToCommentViewModel(comment);

    }
}
