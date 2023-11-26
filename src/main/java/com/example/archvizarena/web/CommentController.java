package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.CommentAddBindingModel;
import com.example.archvizarena.model.service.CommentAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/projects/details/{id}/comments/add")

    public String addComment(@PathVariable Long id,
                             @Valid CommentAddBindingModel commentAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel", bindingResult);

            return "redirect:/projects/details/"+id;
        }

        CommentAddServiceModel commentToBeAdded = new CommentAddServiceModel();
        commentToBeAdded.setProjectId(id);
        commentToBeAdded.setUsername(userDetails.getUsername());
        commentToBeAdded.setTextContent(commentAddBindingModel.getTextContent());

        commentService.saveAndAddComment(commentToBeAdded);

        return "redirect:/projects/details/"+id;
    }
}
