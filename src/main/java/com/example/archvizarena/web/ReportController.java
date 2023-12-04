package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.ReportSubmitBindingModel;
import com.example.archvizarena.model.service.ReportSubmitServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectReportViewModel;
import com.example.archvizarena.service.ProjectService;
import com.example.archvizarena.service.ReportService;
import com.example.archvizarena.service.UserService;
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

    private final UserService userService;

    private final ReportService reportService;


    public ReportController(ProjectService projectService, UserService userService, ReportService reportService) {
        this.projectService = projectService;
        this.userService = userService;

        this.reportService = reportService;
    }

    @GetMapping("/project/{id}")
    public String ReportProject(@PathVariable Long id,
                                Model model) {
        ProjectReportViewModel projectReportViewModel = projectService.getProjectToBeReported(id);

        model.addAttribute("author", projectReportViewModel.getAuthorName());
        model.addAttribute("authorId", projectReportViewModel.getAuthorId());
        model.addAttribute("projectTitle", projectReportViewModel.getTitle());
        model.addAttribute("projectId", projectReportViewModel.getId());

        return "report-project-form";

    }

    @GetMapping("/user/{id}")
    public String ReportUser(@PathVariable Long id,
                             Model model) {

        String name = userService.getNameById(id);
        model.addAttribute("userName", name);
        model.addAttribute("userId", id);
        return "report-user-form";
    }

    @ModelAttribute
    public ReportSubmitBindingModel reportSubmitBindingModel() {
        return new ReportSubmitBindingModel();
    }

    @PostMapping("/project/{id}")
    public String submitReport(@PathVariable Long id,
                               @Valid ReportSubmitBindingModel reportSubmitBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reportSubmitBindingModel", reportSubmitBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportSubmitBindingModel", bindingResult);

            return "redirect:/report/project/" + id;
        }

        ReportSubmitServiceModel report = new ReportSubmitServiceModel();
        report.setReportedProjectId(id);
        report.setReportingUserId(userService.getPrincipalId(userDetails.getUsername()));
        report.setMessage(reportSubmitBindingModel.getMessage());

        reportService.saveProjectReport(report);

        return "redirect:/";
    }

    @PostMapping("/user/{id}")
    public String submitReportUser(@PathVariable Long id,
                               @Valid ReportSubmitBindingModel reportSubmitBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reportSubmitBindingModel", reportSubmitBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportSubmitBindingModel", bindingResult);

            return "redirect:/report/user/" + id;
        }
        ReportSubmitServiceModel report = new ReportSubmitServiceModel();
        report.setReportingUserId(userService.getPrincipalId(userDetails.getUsername()));
        report.setMessage(reportSubmitBindingModel.getMessage());
        report.setReportedUserId(id);

        reportService.saveUserReport(report);

        return "redirect:/";
    }
}
