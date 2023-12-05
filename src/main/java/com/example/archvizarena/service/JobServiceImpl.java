package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.BaseEntity;
import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.repository.ApplicationRepository;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.archvizarena.service.ProjectServiceImpl.isOwner;

@Service
public class JobServiceImpl implements JobService {

    private final JobPublicationRepository jobPublicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JobPublicationMapper jobPublicationMapper;


    private final ApplicationRepository applicationRepository;

    public JobServiceImpl(JobPublicationRepository jobPublicationRepository,
                          UserRepository userRepository,
                          ModelMapper modelMapper,
                          JobPublicationMapper jobPublicationMapper, ApplicationRepository applicationRepository) {
        this.jobPublicationRepository = jobPublicationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jobPublicationMapper = jobPublicationMapper;


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
    public JobPublicationViewModel findJobById(Long id,ArchVizArenaUserDetails userDetails) {
        JobPublicationEntity job=this.lookForTheJob(id);
        if(!job.isActive()){
            if(!isViewerTheOwner(id, userDetails)){
                throw new ObjectNotFoundException("Oops , seems the publication you are looking for cannot be reached!");
            }
        }
        return jobPublicationMapper.mapToJobViewModel(job);
    }

    @Override
    @Transactional
    public void deactivateJob(Long id) {
        JobPublicationEntity jobToBeDeactivated=this.lookForTheJob(id);

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
        JobPublicationEntity jobToBeDeleted = this.lookForTheJob(id);

        UserEntity user = userRepository.findById(userId).orElseThrow();
        user.getJobPublications().remove(jobToBeDeleted);
        userRepository.save(user);

        jobPublicationRepository.delete(jobToBeDeleted);

    }

    @Override
    public void activateJob(Long id) {

        JobPublicationEntity jobToBeActivated = this.lookForTheJob(id);

        jobToBeActivated.setActive(true);
        jobPublicationRepository.save(jobToBeActivated);
    }

    private JobPublicationEntity lookForTheJob(Long id) {
      return   jobPublicationRepository.findById(id).
                orElseThrow(()->new ObjectNotFoundException("The job publication you are searching for " +
                        "does not exist!" +
                        "Maybe you should post it :) "));
    }

    @Override
    public boolean isViewerTheOwner(Long jobId, ArchVizArenaUserDetails viewer) {
        return isOwner(jobId, viewer, userRepository);

    }

    @Override
    @Transactional
    public void deactivateUserJobPublications(Long id) {
        jobPublicationRepository.findAllByBuyer_Id(id).forEach(jobPublicationEntity -> {
                    jobPublicationEntity.setActive(false);
                    jobPublicationRepository.save(jobPublicationEntity);
                });
    }

    @Override
    public Page<JobPublicationViewModel> findAllActiveJobs(Pageable pageable) {
        return jobPublicationRepository.findAllByIsActiveTrue(pageable)
                .map(jobPublicationMapper::mapToJobViewModel);

    }

}
