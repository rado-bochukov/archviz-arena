package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.MessageAddBindingModel;
import com.example.archvizarena.model.service.MessageAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.WorkInProgressViewModel;
import com.example.archvizarena.service.MessageService;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.service.WorkInProgressService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class WorkInProgressController {

    private final WorkInProgressService workInProgressService;
    private final UserService userService;

    private final MessageService messageService;

    public WorkInProgressController(WorkInProgressService workInProgressService, UserService userService, MessageService messageService) {
        this.workInProgressService = workInProgressService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @PreAuthorize("@jobServiceImpl.isViewerTheOwner(#jobId,#userDetails)")
    @PostMapping("/jobs/approval/{jobId}/{applicantId}")

    public String addWorkInProgress(@PathVariable Long jobId,
                                    @PathVariable Long applicantId,
                                    @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        workInProgressService.addWorkInProgress(jobId, applicantId, userService.getPrincipalId(userDetails.getUsername()));

        return "redirect:/users/myProfile";
    }

    @PreAuthorize("@workInProgressServiceImpl.isViewerAParticipant(#id,#userDetails)")
    @GetMapping("/work-in-progress/{id}")
    public String getWorkInProgress(@PathVariable Long id,
                                    Model model,
                                    @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {
        WorkInProgressViewModel workInProgress = workInProgressService.findById(id);

        model.addAttribute("wip", workInProgress);
        return "work-in-progress";
    }

    @ModelAttribute
    public MessageAddBindingModel messageAddBindingModel() {
        return new MessageAddBindingModel();
    }

    @PreAuthorize("@workInProgressServiceImpl.isViewerAParticipant(#id,#userDetails)")
    @PostMapping("/work-in-progress/{id}/messages/add")
    public String getWorkInProgress(@PathVariable Long id,
                                    @Valid MessageAddBindingModel messageAddBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("messageAddBindingModel", messageAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageAddBindingModel", bindingResult);

            return "redirect:/work-in-progress/"+id;
        }

        MessageAddServiceModel messageToBeAdded = new MessageAddServiceModel();
        messageToBeAdded.setWorkInProgressId(id);
        messageToBeAdded.setAuthorUserName(userDetails.getUsername());
        messageToBeAdded.setTextContent(messageAddBindingModel.getTextContent());

        messageService.saveAndAddMessage(messageToBeAdded);

        return "redirect:/work-in-progress/"+id;
    }

}
