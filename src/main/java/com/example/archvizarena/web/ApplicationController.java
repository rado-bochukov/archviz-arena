package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.ApplicationAddBindingModel;
import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.service.ApplicationService;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ApplicationController {
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final UserService userService;


    public ApplicationController(JobService jobService, ApplicationService applicationService, ModelMapper modelMapper, UserService userService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @GetMapping("/jobs/details/{id}/application/add")
    public String getApplyOnJobPublication(@PathVariable Long id,
                                           Model model,
                                           @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        JobPublicationViewModel jobPublication=jobService.findJobById(id,userDetails);
        model.addAttribute("jobPublication", jobPublication);

        if (userDetails != null) {
            Long principalId = userService.getPrincipalId(userDetails.getUsername());
            if(jobPublication.getApplicantsId().contains(principalId)){
                return "successful-application";
            }
            model.addAttribute("principalId", principalId);
            return "job-application-form";
        }
        return "job-application-form";
    }

    @ModelAttribute
    public ApplicationAddBindingModel applicationAddBindingModel(){
        return new ApplicationAddBindingModel();
    }

    @PostMapping("/jobs/details/{id}/application/add")

    public String applyForJob(@PathVariable Long id,
                              @Valid ApplicationAddBindingModel applicationAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("applicationAddBindingModel", applicationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.applicationAddBindingModel", bindingResult);

            return "redirect:/jobs/details/{id}/application/add";
        }

        ApplicationAddServiceModel applicationToBeAdded=new ApplicationAddServiceModel();
        applicationToBeAdded.setAuthor(userDetails.getUsername());
        applicationToBeAdded.setProjectId(id);
        applicationToBeAdded.setTextContent(applicationAddBindingModel.getTextContent());

        applicationService.saveApplication(applicationToBeAdded);

        return "redirect:/jobs/details/{id}/application/add";

    }


}
