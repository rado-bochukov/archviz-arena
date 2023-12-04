package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.PenaltyAddBindingModel;
import com.example.archvizarena.model.view.ReportViewModel;
import com.example.archvizarena.service.PenaltyService;
import com.example.archvizarena.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ReportService reportService;
    private final PenaltyService penaltyService;

    public AdminController(ReportService reportService, PenaltyService penaltyService) {
        this.reportService = reportService;
        this.penaltyService = penaltyService;
    }

    @GetMapping("/reports/users")

    public String getReportedUsers(Model model){

     List<ReportViewModel> reportedUsers =  reportService.findAllActiveUserReports();
     model.addAttribute("reports",reportedUsers);
     model.addAttribute("count",reportedUsers.size());

        return "reported-users";
    }

    @GetMapping("/reports/projects")

    public String getReportedProjects(Model model){
        List<ReportViewModel> reportedProjects=reportService.findAllActiveProjectReports();
        model.addAttribute("reportedProjects",reportedProjects);
        model.addAttribute("count",reportedProjects.size());

        return "reported-projects";
    }

    @PostMapping("/reports/projects/dismiss/{id}")

    public String dismissProjectReport(@PathVariable Long id){
        reportService.dismissReport(id);
        return "redirect:/admin/reports/projects";
    }

    @PostMapping("/reports/users/dismiss/{id}")

    public String dismissUserReport(@PathVariable Long id){
        reportService.dismissReport(id);
        return "redirect:/admin/reports/users";
    }

    @ModelAttribute
    public PenaltyAddBindingModel penaltyAddBindingModel(){
        return new PenaltyAddBindingModel();
    }


    @GetMapping("/sanction/{id}")
    public String getReportedProjects(@PathVariable Long id,
                                      Model model){
        ReportViewModel reportedUser=reportService.findById(id);
        model.addAttribute("report",reportedUser);

        return "sanction-user-form";
    }

    @PostMapping("/sanction/{id}")
    public String getReportedProjects(@PathVariable Long id,
                                      @Valid PenaltyAddBindingModel penaltyAddBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes
                                      ){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("penaltyAddBindingModel", penaltyAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.penaltyAddBindingModel", bindingResult);

            return "redirect:/admin/sanction/"+id;
        }

        penaltyService.addPenalty(penaltyAddBindingModel,id);
        reportService.dismissReport(id);


        return "redirect:/admin/sanction/success";
    }

    @GetMapping("/sanction/success")
    public String getPenalty(){

        return "penalty-added";
    }
}
