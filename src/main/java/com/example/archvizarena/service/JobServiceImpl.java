package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobPublicationRepository jobPublicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public JobServiceImpl(JobPublicationRepository jobPublicationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.jobPublicationRepository = jobPublicationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<JobPublicationViewModel> findAllJobs() {
        return jobPublicationRepository.findAll().stream()
                .map(this::mapToJobViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public void addJob(JobPublicationAddServiceModel jobToBeAdded, String username) {
        JobPublicationEntity job=modelMapper.map(jobToBeAdded,JobPublicationEntity.class);
        UserEntity buyer=userRepository.findByUsername(username).orElseThrow();
        job.setBuyer(buyer);
        job.setPublishedOn(LocalDateTime.now());

        jobPublicationRepository.save(job);

    }

    @Override
    public JobPublicationViewModel findJobById(Long id) {
        JobPublicationEntity job=jobPublicationRepository.findById(id).orElseThrow();
        return this.mapToJobViewModel(job);
    }

    private JobPublicationViewModel mapToJobViewModel(JobPublicationEntity jobPublicationEntity) {

        JobPublicationViewModel job=modelMapper.map(jobPublicationEntity, JobPublicationViewModel.class);
        job.setAuthorName(jobPublicationEntity.getBuyer().getName());
        job.setAuthorCountry(jobPublicationEntity.getBuyer().getCountry());
        List<Long> applicantsIds = jobPublicationEntity.getApplications().stream()
                .map(applicationEntity -> applicationEntity.getApplicant().getId())
                .toList();
        job.setApplicantsId(applicantsIds);

        return job;

    }
}
