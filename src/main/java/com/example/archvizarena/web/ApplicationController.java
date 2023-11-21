package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.ApplicationAddBindingModel;
import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ModelMapper modelMapper;


    public ApplicationController(ApplicationService applicationService, ModelMapper modelMapper) {
        this.applicationService = applicationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/jobs/detail/application/add/{id}")

    public String applyForJob(@PathVariable Long id,
                              ApplicationAddBindingModel applicationAddBindingModel,
                              @AuthenticationPrincipal ArchVizArenaUserDetails userDetails){

        ApplicationAddServiceModel applicationToBeAdded=new ApplicationAddServiceModel();
        applicationToBeAdded.setAuthor(userDetails.getUsername());
        applicationToBeAdded.setProjectId(id);
        applicationToBeAdded.setTextContent(applicationAddBindingModel.getTextContent());

        applicationService.saveApplication(applicationToBeAdded);

        return "redirect:/";

    }


}
