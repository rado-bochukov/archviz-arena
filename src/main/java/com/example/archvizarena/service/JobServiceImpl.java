package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.view.JobPublicationBrowseViewModel;
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
    public List<JobPublicationBrowseViewModel> findAllJobs() {
        return jobPublicationRepository.findAll().stream()
                .map(this::mapToBrowseViewModel)
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

    private JobPublicationBrowseViewModel  mapToBrowseViewModel(JobPublicationEntity jobPublicationEntity) {

        JobPublicationBrowseViewModel job=modelMapper.map(jobPublicationEntity, JobPublicationBrowseViewModel.class);
        job.setAuthorName(jobPublicationEntity.getBuyer().getName());
        job.setAuthorCountry(jobPublicationEntity.getBuyer().getCountry());

        return job;

    }
}
