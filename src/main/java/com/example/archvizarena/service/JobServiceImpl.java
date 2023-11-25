package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.ApplicationEntity;
import com.example.archvizarena.model.entity.BaseEntity;
import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.repository.ApplicationRepository;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobPublicationRepository jobPublicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JobPublicationMapper jobPublicationMapper;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

    public JobServiceImpl(JobPublicationRepository jobPublicationRepository,
                          UserRepository userRepository,
                          ModelMapper modelMapper,
                          JobPublicationMapper jobPublicationMapper, ApplicationService applicationService, ApplicationRepository applicationRepository) {
        this.jobPublicationRepository = jobPublicationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jobPublicationMapper = jobPublicationMapper;
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<JobPublicationViewModel> findAllJobs() {
        return jobPublicationRepository.findAll().stream()
                .filter(JobPublicationEntity::isActive)
                .map(jobPublicationMapper::mapToJobViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public void addJob(JobPublicationAddServiceModel jobToBeAdded, String username) {
        JobPublicationEntity job=modelMapper.map(jobToBeAdded,JobPublicationEntity.class);
        UserEntity buyer=userRepository.findByUsername(username).orElseThrow();
        job.setBuyer(buyer);
        job.setPublishedOn(LocalDateTime.now());
        job.setActive(true);

        jobPublicationRepository.save(job);

    }

    @Override
    public JobPublicationViewModel findJobById(Long id) {
        JobPublicationEntity job=jobPublicationRepository.findById(id).orElseThrow();
        return jobPublicationMapper.mapToJobViewModel(job);
    }

    @Override
    @Transactional
    public void deactivateJob(Long id, Long userId) {
        JobPublicationEntity jobToBeDeactivated=jobPublicationRepository.findById(id).orElseThrow();

        // TODO: 25.11.2023 г. error handling
        // TODO: 25.11.2023 г. get this validation in method

        if(!Objects.equals(jobToBeDeactivated.getBuyer().getId(), userId)){
            throw new RuntimeException("this user cannot deactivate the publication");
        }

        jobToBeDeactivated.setActive(false);
        List<Long> applicationsToBeRemoved = jobToBeDeactivated.getApplications()
                .stream().map(BaseEntity::getId)
                .toList();
        jobToBeDeactivated.setApplications(new ArrayList<>());
        applicationRepository.deleteAllByIdInBatch(applicationsToBeRemoved);

        jobPublicationRepository.save(jobToBeDeactivated);
    }

    @Override
    @Transactional
    public void deleteJob(Long id, Long userId) {
        JobPublicationEntity jobToBeDeleted=jobPublicationRepository.findById(id).orElseThrow();
        if(!Objects.equals(jobToBeDeleted.getBuyer().getId(), userId)){
            throw new RuntimeException("this user cannot deactivate the publication");
        }
        UserEntity user = userRepository.findById(userId).orElseThrow();
        user.getJobPublications().remove(jobToBeDeleted);
        userRepository.save(user);

        jobPublicationRepository.delete(jobToBeDeleted);

    }

    @Override
    public void activateJob(Long id, Long userId) {
        JobPublicationEntity jobToBeActivated=jobPublicationRepository.findById(id).orElseThrow();

        if(!Objects.equals(jobToBeActivated.getBuyer().getId(), userId)){
            throw new RuntimeException("this user cannot deactivate the publication");
        }
        jobToBeActivated.setActive(true);
        jobPublicationRepository.save(jobToBeActivated);
    }

}
