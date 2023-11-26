package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.JobPublicationAddBindingModel;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jobs")
public class JobPublicationController {

    private final JobService jobService;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public JobPublicationController(JobService jobService, ModelMapper modelMapper, UserService userService) {
        this.jobService = jobService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String getJobAdd() {
        return "job-add";
    }

    @ModelAttribute
    public JobPublicationAddBindingModel jobPublicationAddBindingModel() {
        return new JobPublicationAddBindingModel();
    }

    @PostMapping("/add")

    public String addJob(@Valid JobPublicationAddBindingModel jobPublicationAddBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("jobPublicationAddBindingModel", jobPublicationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.jobPublicationAddBindingModel", bindingResult);

            return "redirect:/jobs/add";
        }

        jobService.addJob(modelMapper.map(jobPublicationAddBindingModel, JobPublicationAddServiceModel.class), userDetails.getUsername());

        return "redirect:/";
    }

    @PreAuthorize("@jobServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @PostMapping("/deactivate/{id}")
    public String deactivateJob(@PathVariable Long id,
                                @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        // TODO: 25.11.2023 г. error handling1

        jobService.deactivateJob(id);

        return "redirect:/users/myProfile";
    }

    @PreAuthorize("@jobServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @DeleteMapping ("/delete/{id}")
    public String deleteJob(@PathVariable Long id,
                            @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        // TODO: 25.11.2023 г. error handling2

        Long userId = userService.getPrincipalId(userDetails.getUsername());
        jobService.deleteJob(id, userId);

        return "redirect:/users/myProfile";
    }

    @PreAuthorize("@jobServiceImpl.isViewerTheOwner(#id,#userDetails)")
    @PostMapping("activate/{id}")
    public String activateJob(@PathVariable Long id,
                              @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        // TODO: 25.11.2023 г. error handling3

        jobService.activateJob(id);

        return "redirect:/users/myProfile";
    }

}
