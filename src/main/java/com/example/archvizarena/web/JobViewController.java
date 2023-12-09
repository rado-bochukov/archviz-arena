package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.JobPublicationSearchBindingModel;
import com.example.archvizarena.model.binding.ProjectSearchBindingModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.CurrentApplicantViewModel;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.model.view.ProjectBrowsingViewModel;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String getAllJobs(Model model,
                            @PageableDefault (size = 6) Pageable pageable) {

        Page<JobPublicationViewModel> allActiveJobs = jobService.findAllActiveJobs(pageable);
        model.addAttribute("allJobs", allActiveJobs);
        model.addAttribute("jobsCount", allActiveJobs.getTotalElements());

        return "jobs-browse";
    }

    @GetMapping("/details/{id}")
    public String getJobDetails(@PathVariable Long id,
                                Model model,
                                @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {


        JobPublicationViewModel jobPublication=jobService.findJobById(id,userDetails);
        model.addAttribute("jobPublication", jobPublication);

        if (userDetails != null) {
            Long principalId = userService.getPrincipalId(userDetails.getUsername());
            model.addAttribute("principalId", principalId);
            return "job-publication-details";
        }
        return "job-publication-details";
    }

    @ModelAttribute
    public JobPublicationSearchBindingModel jobPublicationSearchBindingModel(){
        return new JobPublicationSearchBindingModel();
    }

    @GetMapping("/search")
    public String searchJobs(@Valid JobPublicationSearchBindingModel jobPublicationSearchBindingModel,
                                Model model,
                                Pageable pageable) {


        if (!jobPublicationSearchBindingModel.isEmpty()) {
            Page<JobPublicationViewModel> foundJobs = jobService.searchJobPublications(jobPublicationSearchBindingModel, pageable);
            int foundJobPublications = foundJobs.getContent().size();
            model.addAttribute("allJobs", foundJobs);
            model.addAttribute("jobsCount", foundJobPublications);
        }

        return "jobs-browse";
    }

}
