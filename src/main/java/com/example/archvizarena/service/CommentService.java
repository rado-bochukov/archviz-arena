package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.CommentEntity;
import com.example.archvizarena.model.service.CommentAddServiceModel;
import com.example.archvizarena.model.view.CommentViewModel;

public interface CommentService {
    CommentViewModel mapToCommentViewModel(CommentEntity c);
    void saveAndAddComment(CommentAddServiceModel commentToBeAdded);

}
