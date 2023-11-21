package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.JobPublicationAddBindingModel;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.JobService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jobs")
public class JobPublicationAddController {

    private final JobService jobService;
    private final ModelMapper modelMapper;


    public JobPublicationAddController(JobService jobService, ModelMapper modelMapper) {
        this.jobService = jobService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String getJobAdd(){
        return "job-add";
    }

    @ModelAttribute
    public JobPublicationAddBindingModel jobPublicationAddBindingModel(){
        return new JobPublicationAddBindingModel();
    }

    @PostMapping("/add")

    public String addJob(@Valid JobPublicationAddBindingModel jobPublicationAddBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("jobPublicationAddBindingModel", jobPublicationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.jobPublicationAddBindingModel", bindingResult);

            return "redirect:/jobs/add";
        }

        jobService.addJob(modelMapper.map(jobPublicationAddBindingModel, JobPublicationAddServiceModel.class),userDetails.getUsername());

        return "redirect:/";
    }

}
