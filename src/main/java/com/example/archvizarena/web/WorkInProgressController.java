package com.example.archvizarena.web;

import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.UserService;
import com.example.archvizarena.service.WorkInProgressService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkInProgressController {

    private final WorkInProgressService workInProgressService;
    private final UserService userService;

    public WorkInProgressController(WorkInProgressService workInProgressService, UserService userService) {
        this.workInProgressService = workInProgressService;
        this.userService = userService;
    }

    @PostMapping("/jobs/approval/{jobId}/{applicantId}")

    public String addWorkInProgress(@PathVariable Long jobId,
                                    @PathVariable Long applicantId,
                                    @AuthenticationPrincipal ArchVizArenaUserDetails userDetails) {

        workInProgressService.addWorkInProgress(jobId,applicantId,userService.getPrincipalId(userDetails.getUsername()));

        return "redirect:/users/myProfile";
    }

    // TODO: 25.11.2023 г. сортиране на обявите

}
