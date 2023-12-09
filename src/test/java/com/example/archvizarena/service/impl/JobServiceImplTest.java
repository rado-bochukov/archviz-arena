package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.ApplicationEntity;
import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.ReportEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.repository.ApplicationRepository;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.testUtils.UnitTestUtil;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.createBuyerEntity;
import static com.example.archvizarena.testUtils.UnitTestUtil.createJobPublication;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    private JobService jobServiceToTest;

    @Mock
    private JobPublicationRepository jobPublicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;


    private JobPublicationMapper jobPublicationMapper;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ArchVizArenaUserDetails archVizArenaUserDetails;

    private JobPublicationEntity jobPublication;
    private UserEntity buyer;

    @BeforeEach
    void setUp() {
        jobServiceToTest = new JobServiceImpl(
                jobPublicationRepository,
                userRepository,
                modelMapper,
                new JobPublicationMapper(),
                applicationRepository
        );

        jobPublication = createJobPublication();
        jobPublication.setApplications(new ArrayList<>());
        buyer = createBuyerEntity();
    }

    @Test
    void testJobPublicationNotFound() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> jobServiceToTest.findJobById(2L, archVizArenaUserDetails));
    }

    @Test
    void testFindAllJobs_shouldReturnEmptyList() {

        jobPublication.setActive(false);
        when(jobPublicationRepository.findAll()).thenReturn(List.of(jobPublication));

        List<JobPublicationViewModel> allJobs = jobServiceToTest.findAllJobs();

        Assertions.assertEquals(0, allJobs.size());

    }

    @Test
    void testFindAllJobs_shouldReturnListWithSize_1() {

        jobPublication.setActive(true);
        jobPublication.setBuyer(buyer);
        when(jobPublicationRepository.findAll()).thenReturn(List.of(jobPublication));

        List<JobPublicationViewModel> allJobs = jobServiceToTest.findAllJobs();

        Assertions.assertEquals(1, allJobs.size());

    }

    @Test
    void testAddJob() {
        JobPublicationAddServiceModel jobPublicationAddServiceModel=new JobPublicationAddServiceModel();

        when(userRepository.findByUsername(buyer.getUsername())).thenReturn(Optional.of(buyer));
        when(modelMapper.map(jobPublicationAddServiceModel,JobPublicationEntity.class)).thenReturn(jobPublication);

        jobServiceToTest.addJob(jobPublicationAddServiceModel,buyer.getUsername());

        verify(userRepository, times(1)).findByUsername(buyer.getUsername());
        verify(jobPublicationRepository, times(1)).save(jobPublication);

    }

    @Test
    void testFindJobByIdSuccessful(){
        jobPublication.setActive(true);
        jobPublication.setBuyer(buyer);

        when(jobPublicationRepository.findById(jobPublication.getId()))
                .thenReturn(Optional.of(jobPublication));

        JobPublicationViewModel jobById = jobServiceToTest.findJobById(jobPublication.getId(), archVizArenaUserDetails);

        assertEquals(jobPublication.getId(),jobById.getId());
        assertEquals(jobPublication.getBuyer().getName(),jobById.getAuthorName());
        assertEquals(jobPublication.getBudget(),jobById.getBudget());
        assertEquals(jobPublication.getDescription(),jobById.getDescription());

    }

    @Test
    void testDeactivateJob(){
        jobPublication.setActive(true);
        jobPublication.setBuyer(buyer);
        ApplicationEntity application=new ApplicationEntity();
        application.setId(1L);
        jobPublication.setApplications(List.of(application));
        when(jobPublicationRepository.findById(jobPublication.getId()))
                .thenReturn(Optional.of(jobPublication));

        jobServiceToTest.deactivateJob(jobPublication.getId());

        Assertions.assertFalse(jobPublication.isActive());
        verify(applicationRepository, times(1))
                .deleteAllByIdInBatch(List.of(application.getId()));
        verify(jobPublicationRepository, times(1))
                .save(jobPublication);

    }

    @Test
    void testActivateJob(){
        jobPublication.setActive(false);
        jobPublication.setBuyer(buyer);
        when(jobPublicationRepository.findById(jobPublication.getId()))
                .thenReturn(Optional.of(jobPublication));

        jobServiceToTest.activateJob(jobPublication.getId());

        Assertions.assertTrue(jobPublication.isActive());

        verify(jobPublicationRepository, times(1))
                .save(jobPublication);

    }

    @Test
    void testDeleteJob(){
        jobPublication.setActive(false);
        jobPublication.setBuyer(buyer);
        List<JobPublicationEntity> buyersPublications=new ArrayList<>();
        buyersPublications.add(jobPublication);
        buyer.setJobPublications(buyersPublications);
        when(jobPublicationRepository.findById(jobPublication.getId()))
                .thenReturn(Optional.of(jobPublication));
        when(userRepository.findById(buyer.getId())).thenReturn(Optional.of(buyer));

        jobServiceToTest.deleteJob(jobPublication.getId(),buyer.getId());

       Assertions.assertEquals(0,buyer.getJobPublications().size());

        verify(jobPublicationRepository, times(1))
                .delete(jobPublication);
        verify(userRepository, times(1))
                .save(buyer);

    }




}