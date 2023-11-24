package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
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
    private final JobPublicationMapper jobPublicationMapper;

    public JobServiceImpl(JobPublicationRepository jobPublicationRepository, UserRepository userRepository, ModelMapper modelMapper, JobPublicationMapper jobPublicationMapper) {
        this.jobPublicationRepository = jobPublicationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jobPublicationMapper = jobPublicationMapper;
    }

    @Override
    public List<JobPublicationViewModel> findAllJobs() {
        return jobPublicationRepository.findAll().stream()
                .map(jobPublicationMapper::mapToJobViewModel)
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
        return jobPublicationMapper.mapToJobViewModel(job);
    }

}
