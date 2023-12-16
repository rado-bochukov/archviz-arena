package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.ReportEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.ReportSubmitServiceModel;
import com.example.archvizarena.model.view.ReportViewModel;
import com.example.archvizarena.repository.ProjectRepository;
import com.example.archvizarena.repository.ReportRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.ReportService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<ReportViewModel> findAllActiveUserReports() {

        return reportRepository.findAllByReportedProjectNull().stream()
                .filter(r->!r.isArchived())
                .map(this::mapFromEntityToView)
                .toList();
    }



    @Override
    public List<ReportViewModel> findAllActiveProjectReports() {
        return reportRepository.findAllByReportedProjectNotNull().stream()
                .filter(r->!r.isArchived())
                .map(this::mapFromEntityToView)
                .toList();
    }

    @Override
    public void dismissReport(Long id) {
        ReportEntity reportEntity= getReportEntity(id);
        reportEntity.setArchived(true);
        reportEntity.setArchivedUntil(LocalDateTime.now().plusDays(7));
        reportRepository.save(reportEntity);
    }


    @Override
    public ReportViewModel findById(Long id) {
       ReportEntity reportEntity=getReportEntity(id);
        return mapFromEntityToView(reportEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public void clearArchivedReports() {
        reportRepository.findAllByIsArchivedTrue()
                .forEach(reportEntity -> {
                    if(reportEntity.getArchivedUntil().isBefore(LocalDateTime.now())){
                        reportRepository.deleteById(reportEntity.getId());
                    }
                });
    }

    private ReportEntity getReportEntity(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Oops, the report you are looking for does not exist!"));
    }
    private ReportViewModel mapFromEntityToView(ReportEntity reportEntity) {

        ReportViewModel report=new ReportViewModel();
        report.setReportId(reportEntity.getId());
        report.setReportingUser(reportEntity.getReportingUser().getName());
        report.setReportedUser(reportEntity.getReportedUser().getName());
        report.setMessage(reportEntity.getMessage());

        if(reportEntity.getReportedProject()!=null){
            report.setReportedProjectId(reportEntity.getReportedProject().getId());
            report.setReportedProjectTitle(reportEntity.getReportedProject().getTitle());
        }

        return report;
    }

    private ReportEntity fillTheGeneralInfo(ReportSubmitServiceModel report) {
        ReportEntity reportEntity=new ReportEntity();
        UserEntity reportingUser = userRepository.findById(report.getReportingUserId())
                .orElseThrow(() -> new ObjectNotFoundException("The user you are looking for does not exist!"));
        reportEntity.setReportingUser(reportingUser);
        reportEntity.setMessage(report.getMessage());
        reportEntity.setArchived(false);
        return reportEntity;
    }
}
