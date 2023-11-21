package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.ApplicationAddBindingModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.CurrentApplicantViewModel;
import com.example.archvizarena.model.view.JobPublicationBrowseViewModel;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobViewController {

    private final JobService jobService;
    private final UserService userService;

    public JobViewController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @ModelAttribute(name= "newApplication")
    public ApplicationAddBindingModel applicationAddBindingModel(){
        return new ApplicationAddBindingModel();
    }
    @GetMapping("/all")
    public String getAllJobs(Model model,
                             @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        List<JobPublicationBrowseViewModel> allJobs = jobService.findAllJobs();
        model.addAttribute("allJobs", allJobs);


        if (userDetails != null) {
            CurrentApplicantViewModel currentUser = userService.findCurrentApplicantInfo(userDetails.getUsername());
            model.addAttribute("currentUser", currentUser);
            return "jobs-browse";
        }
        return "jobs-browse";
    }
}
