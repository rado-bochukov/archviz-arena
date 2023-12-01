package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.ReportEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.ReportSubmitServiceModel;
import com.example.archvizarena.model.view.ReportedUserViewModel;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.ReportRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ReportRepository reportRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public void saveProjectReport(ReportSubmitServiceModel reportToBeSaved) {
        ReportEntity reportEntity=fillTheGeneralInfo(reportToBeSaved);

        PortfolioProjectEntity reportedProject = projectRepository.findById(reportToBeSaved.getReportedProjectId())
                .orElseThrow(() -> new ObjectNotFoundException("The project you are looking for does not exist!"));

        reportEntity.setReportedProject(reportedProject);
        reportEntity.setReportedUser(reportedProject.getAuthor());

        reportRepository.save(reportEntity);
    }

    @Override
    public void saveUserReport(ReportSubmitServiceModel report) {
      ReportEntity reportEntity=fillTheGeneralInfo(report);
        UserEntity reportedUser = userRepository.findById(report.getReportedUserId())
                .orElseThrow(() -> new ObjectNotFoundException("The user you are looking for does not exist!"));
        reportEntity.setReportedUser(reportedUser);
        reportRepository.save(reportEntity);
    }

    @Override
    public List<ReportedUserViewModel> findAllUserReports() {

        return reportRepository.findAllByReportedProjectNull().stream()
                .map(this::mapFromEntityToUserView)
                .toList();
    }

    private ReportedUserViewModel mapFromEntityToUserView(ReportEntity r) {
        ReportedUserViewModel report=new ReportedUserViewModel();
        report.setReportId(r.getId());
        report.setReportedUser(r.getReportedUser().getName());
        report.setReportingUser(r.getReportingUser().getName());
        report.setMessage(r.getMessage());
        report.setReportedUserId(r.getReportedUser().getId());
        report.setReportingUserId(r.getReportingUser().getId());
        return report;
    }

    private ReportEntity fillTheGeneralInfo(ReportSubmitServiceModel report) {
        ReportEntity reportEntity=new ReportEntity();
        UserEntity reportingUser = userRepository.findById(report.getReportingUserId())
                .orElseThrow(() -> new ObjectNotFoundException("The user you are looking for does not exist!"));
        reportEntity.setReportingUser(reportingUser);
        reportEntity.setMessage(report.getMessage());
        reportEntity.setChecked(false);
        return reportEntity;
    }
}
