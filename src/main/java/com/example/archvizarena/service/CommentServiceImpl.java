package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.CommentEntity;
import com.example.archvizarena.model.view.CommentViewModel;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    @Override
    public CommentViewModel mapToCommentViewModel(CommentEntity comment) {

        CommentViewModel commentViewModel=new CommentViewModel();
        commentViewModel.setAuthorName(comment.getCommentAuthor().getName());
        commentViewModel.setCreated(comment.getCreated());
        commentViewModel.setTextContent(comment.getTextContent());
        return commentViewModel;
    }
}
