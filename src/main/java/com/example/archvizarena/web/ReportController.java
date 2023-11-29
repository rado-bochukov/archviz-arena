package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.ReportSubmitBindingModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.CommentViewModel;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import com.example.archvizarena.service.CommentService;
import com.example.archvizarena.service.ProjectService;
import com.example.archvizarena.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ProjectService projectService;
    private final CommentService commentService;

    private final ReportService reportService;

    public ReportController(ProjectService projectService, CommentService commentService, ReportService reportService) {
        this.projectService = projectService;
        this.commentService = commentService;
        this.reportService = reportService;
    }

    @GetMapping("/{projectId}/{commentId}")
    public String ReportComment(@PathVariable Long projectId,
                                @PathVariable Long commentId,
                                Model model) {
        ProjectReportViewModel projectReportViewModel = projectService.getProjectToBeReported(projectId);
        CommentViewModel comment = commentService.findById(commentId);

        model.addAttribute("author", projectReportViewModel.getAuthorName());
        model.addAttribute("authorId", projectReportViewModel.getAuthorId());
        model.addAttribute("projectTitle", projectReportViewModel.getTitle());
        model.addAttribute("projectId", projectReportViewModel.getId());
        model.addAttribute("comment", comment.getTextContent());

        return "report-form";

    }

    @ModelAttribute
    public ReportSubmitBindingModel reportSubmitBindingModel() {
        return new ReportSubmitBindingModel();
    }

    @PostMapping("/submit")
    public String submitReport(@Valid ReportSubmitBindingModel reportSubmitBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        System.out.println("Hello");
      return "redirect:/";
    }
}
