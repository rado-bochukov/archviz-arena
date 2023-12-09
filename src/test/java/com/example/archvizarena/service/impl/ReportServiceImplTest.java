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
import com.example.archvizarena.testUtils.UnitTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    private ReportService reportServiceToTest;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReportRepository reportRepository;

    private ReportEntity testReportEntity;

    private ReportSubmitServiceModel testReportSubmit;
    private UserEntity reportingUser;

    private UserEntity reportedUser;

    private PortfolioProjectEntity reportedProject;

    @BeforeEach
    void setUp() {
        reportServiceToTest = new ReportServiceImpl(
                projectRepository,
                userRepository,
                reportRepository);

        testReportEntity = createReport();
        reportingUser = createArtistEntity();
        reportedUser = createBuyerEntity();
        testReportSubmit = createReportSubmitServiceModel();
        testReportSubmit.setReportedUserId(reportedUser.getId());
        testReportSubmit.setReportingUserId(reportingUser.getId());
        testReportEntity.setReportedUser(reportedUser);
        testReportEntity.setReportingUser(reportingUser);
    }

    @Test
    void testReportNotFound() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> reportServiceToTest.findById(2L));
    }

    @Test
    void testSaveUserReport() {
        when(userRepository.findById(reportingUser.getId())).thenReturn(Optional.of(reportingUser));
        when(userRepository.findById(reportedUser.getId())).thenReturn(Optional.of(reportedUser));

        reportServiceToTest.saveUserReport(testReportSubmit);

        verify(userRepository, times(1)).findById(reportingUser.getId());
        verify(userRepository, times(1)).findById(reportedUser.getId());
        verify(reportRepository, times(1)).save(any(ReportEntity.class));

    }

    @Test
    void testSaveProjectReport() {

        reportedProject = createProject();
        reportedProject.setId(1L);
        testReportEntity.setReportedProject(reportedProject);
        testReportSubmit.setReportedProjectId(reportedProject.getId());
        when(userRepository.findById(reportingUser.getId())).thenReturn(Optional.of(reportingUser));
        when(projectRepository.findById(testReportSubmit.getReportedProjectId())).thenReturn(Optional.of(reportedProject));

        reportServiceToTest.saveProjectReport(testReportSubmit);

        verify(userRepository, times(1)).findById(reportingUser.getId());
        verify(projectRepository, times(1)).findById(testReportSubmit.getReportedProjectId());
        verify(reportRepository, times(1)).save(any(ReportEntity.class));
    }

    @Test
    void testFindAllActiveProjectReports_shouldReturn_emptyList() {

        reportedProject = createProject();
        reportedProject.setId(1L);
        PortfolioProjectEntity project2 = reportedProject;
        project2.setId(2L);
        testReportEntity.setReportedProject(reportedProject);
        testReportEntity.setArchived(true);

        when(reportRepository.findAllByReportedProjectNotNull())
                .thenReturn(List.of(
                        testReportEntity
                       ));

        List<ReportViewModel> allActiveProjectReports = reportServiceToTest.findAllActiveProjectReports();
        Assertions.assertEquals(0,allActiveProjectReports.size());
    }

    @Test
    void testFindAllActiveProjectReports_shouldReturn_1() {

        reportedProject = createProject();
        reportedProject.setId(1L);
        PortfolioProjectEntity project2 = reportedProject;
        project2.setId(2L);
        testReportEntity.setReportedProject(reportedProject);
        testReportEntity.setArchived(false);

        when(reportRepository.findAllByReportedProjectNotNull())
                .thenReturn(List.of(
                        testReportEntity
                ));

        List<ReportViewModel> allActiveProjectReports = reportServiceToTest.findAllActiveProjectReports();
        Assertions.assertEquals(1,allActiveProjectReports.size());
    }

    @Test
    void testFindAllActiveUsersReports_shouldReturn_1() {

        reportedProject = createProject();
        reportedProject.setId(1L);
        PortfolioProjectEntity project2 = reportedProject;
        project2.setId(2L);
        testReportEntity.setReportedProject(reportedProject);
        testReportEntity.setArchived(false);

        when(reportRepository.findAllByReportedProjectNull())
                .thenReturn(List.of(
                        testReportEntity
                ));

        List<ReportViewModel> allActiveUserReports = reportServiceToTest.findAllActiveUserReports();
        Assertions.assertEquals(1,allActiveUserReports.size());
    }

    @Test
    void testDismissReport(){
        testReportEntity.setArchived(false);

        when(reportRepository.findById(testReportEntity.getId()))
                .thenReturn(Optional.of(testReportEntity));

        reportServiceToTest.dismissReport(testReportEntity.getId());

        Assertions.assertTrue(testReportEntity.isArchived());



    }
}