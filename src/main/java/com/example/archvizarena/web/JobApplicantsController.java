package com.example.archvizarena.web;

import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ApplicationViewModel;
import com.example.archvizarena.model.view.JobPublicationBrowseCandidatesViewModel;
import com.example.archvizarena.service.ApplicationService;
import com.example.archvizarena.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobApplicantsController {

    private final ApplicationService applicationService;
    private final JobService jobService;
    private final ModelMapper modelMapper;

    public JobApplicantsController(ApplicationService applicationService, JobService jobService, ModelMapper modelMapper) {
        this.applicationService = applicationService;
        this.jobService = jobService;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("@jobServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @GetMapping("/job-applicants/{id}")
    public String getJobApplicants(@PathVariable Long id,
                                   Model model,
                                   @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        JobPublicationBrowseCandidatesViewModel job=modelMapper.map(jobService.findJobById(id,userDetails),JobPublicationBrowseCandidatesViewModel.class);
        List<ApplicationViewModel> applications=applicationService.findJobApplications(id);
        model.addAttribute("applications",applications);
        model.addAttribute("job",job);

        return "job-applicants";
    }

}
