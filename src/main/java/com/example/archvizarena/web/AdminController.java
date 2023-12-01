package com.example.archvizarena.web;

import com.example.archvizarena.model.view.ReportedUserViewModel;
import com.example.archvizarena.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ReportService reportService;

    public AdminController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports/users")

    public String getReportedUsers(Model model){

     List<ReportedUserViewModel> reportedUsers =  reportService.findAllUserReports();
     model.addAttribute("reports",reportedUsers);
     model.addAttribute("count",reportedUsers.size());

        return "reported-users";
    }
}
