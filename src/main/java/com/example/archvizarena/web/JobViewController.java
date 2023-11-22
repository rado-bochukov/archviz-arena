package com.example.archvizarena.web;

import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.CurrentApplicantViewModel;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/all")
    public String getAllJobs(Model model) {

        List<JobPublicationViewModel> allJobs = jobService.findAllJobs();
        model.addAttribute("allJobs", allJobs);

        return "jobs-browse";
    }

    @GetMapping("/details/{id}")
    public String getJobDetails(@PathVariable Long id,
                                Model model,
                                @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        JobPublicationViewModel jobPublication=jobService.findJobById(id);
        model.addAttribute("jobPublication", jobPublication);

        if (userDetails != null) {

            CurrentApplicantViewModel currentUser = userService.findCurrentApplicantInfo(userDetails.getUsername());
            model.addAttribute("currentUser", currentUser);
            return "job-publication-details";
        }
        return "job-publication-details";
    }
}
