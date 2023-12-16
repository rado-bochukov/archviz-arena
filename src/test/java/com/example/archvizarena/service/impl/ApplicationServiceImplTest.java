package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.ApplicationEntity;
import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.model.view.ApplicationViewModel;
import com.example.archvizarena.repository.ApplicationRepository;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.ApplicationService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.testUtils.UnitTestUtil;
import com.example.archvizarena.util.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    private ApplicationService applicationServiceToTest;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private JobPublicationRepository jobPublicationRepository;
    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserEntity buyer;

    private UserEntity applicant;

    private JobPublicationEntity jobPublication;
    private ApplicationAddServiceModel testApplicationToBeAdded;
    private ApplicationEntity application;

    @BeforeEach
    void setUp() {
        applicationServiceToTest = new ApplicationServiceImpl(
                applicationRepository,
                jobPublicationRepository,
                userRepository,
               new UserMapper()
        );

        jobPublication = createJobPublication();
        applicant = createArtistEntity();
        buyer = createBuyerEntity();
        application = createApplication();
        testApplicationToBeAdded = createApplicationServiceModel();


    }

    @Test
    void testSaveApplication() {
        testApplicationToBeAdded.setProjectId(jobPublication.getId());
        testApplicationToBeAdded.setAuthor(applicant.getUsername());

        when(userRepository.findByUsername(testApplicationToBeAdded.getAuthor())).thenReturn(Optional.of(applicant));
        when(jobPublicationRepository.findById(jobPublication.getId())).thenReturn(Optional.of(jobPublication));

        applicationServiceToTest.saveApplication(testApplicationToBeAdded);

        verify(userRepository, times(1)).findByUsername(applicant.getUsername());
        verify(jobPublicationRepository, times(1)).findById(jobPublication.getId());
        verify(applicationRepository, times(1)).save(any(ApplicationEntity.class));
    }

    @Test
    void testFindJobApplicationByJobId_should_return_testApplication() {
       application.setApplicant(applicant);
       application.setJobPublication(jobPublication);
       application.setSubmittedOn(LocalDateTime.now());

       when(applicationRepository.findByJobPublication_Id(jobPublication.getId())).thenReturn(List.of(application));



        List<ApplicationViewModel> jobApplications = applicationServiceToTest.findJobApplications(jobPublication.getId());

       Assertions.assertEquals(1,jobApplications.size());
       Assertions.assertEquals(application.getApplicant().getName(),jobApplications.get(0).getApplicant().getName());
    }

}