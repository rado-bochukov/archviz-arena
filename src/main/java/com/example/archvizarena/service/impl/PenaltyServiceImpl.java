package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.binding.PenaltyAddBindingModel;
import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.repository.PenaltyRepository;
import com.example.archvizarena.repository.ReportRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.PenaltyService;
import com.example.archvizarena.service.ProjectService;
import com.example.archvizarena.service.ReportService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PenaltyServiceImpl implements PenaltyService {

    private final ReportRepository reportRepository;

    private final ReportService reportService;
    private final PenaltyRepository penaltyRepository;
    private final ProjectService projectService;
    private final JobService jobService;
    private final UserRepository userRepository;


    public PenaltyServiceImpl(ReportRepository reportRepository, ReportService reportService, PenaltyRepository penaltyRepository, ProjectService projectService, JobService jobService, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.reportService = reportService;
        this.penaltyRepository = penaltyRepository;
        this.projectService = projectService;
        this.jobService = jobService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addPenalty(PenaltyAddBindingModel penaltyAddBindingModel, Long id) {

        ReportEntity reportEntity = reportRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("The report you are looking for does not exist!"));

        UserEntity reportedUser = reportEntity.getReportedUser();
        if (reportedUser.isMuted()) {
            throw new IllegalStateException("The user is already sanctioned!");
        }
        PortfolioProjectEntity reportedProject = reportEntity.getReportedProject();
        List<Long> reportsToBeDeleted = reportRepository.findAllByReportedUser_Id(reportedUser.getId())
                .stream()
                .map(BaseEntity::getId)
                .toList();
        for (Long aLong : reportsToBeDeleted) {
            reportService.deleteById(aLong);
        }

        if (reportedProject != null) {
            projectService.deleteProject(reportedProject.getId());
        }
        PenaltyEntity penaltyEntity = new PenaltyEntity();
        penaltyEntity.setPenalizedUser(reportedUser);
        penaltyEntity.setPenalty(penaltyAddBindingModel.getPenaltyType());
        penaltyEntity.setMessage(penaltyAddBindingModel.getPenaltyMessage());
        penaltyEntity.setEndDate(LocalDateTime.now().plusDays(7));

//        if (penaltyAddBindingModel.getPenaltyType().equals(PenaltyTypeEnum.BLOCK)) {
//            reportedUser.setBlocked(true);
//            if (reportedUser.getUserOccupation().equals(UserOccupationEnum.ARTIST)) {
//                projectService.deactivateUserProjects(reportedUser.getId());
//            }
//            jobService.deactivateUserJobPublications(reportedUser.getId());
//        } else {
//            reportedUser.setMuted(true);
//        }
        reportedUser.setMuted(true);

        userRepository.save(reportedUser);
        penaltyRepository.save(penaltyEntity);
    }

    @Override
    @Transactional
    public void unmuteUsersWithExpiredMute() {
        penaltyRepository.findAll().forEach(penalty -> {
                    if (penalty.getEndDate().isBefore(LocalDateTime.now())) {
                        UserEntity user = userRepository.findById(penalty.getPenalizedUser().getId())
                                .orElseThrow(() -> new ObjectNotFoundException("The user you are looking for does not exist"));
                        user.setMuted(false);
                        userRepository.save(user);
                        penaltyRepository.delete(penalty);
                    }
                }
        );
    }
}
