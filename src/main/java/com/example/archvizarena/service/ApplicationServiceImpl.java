package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.ApplicationEntity;
import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.repository.ApplicationRepository;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPublicationRepository jobPublicationRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, JobPublicationRepository jobPublicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobPublicationRepository = jobPublicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveApplication(ApplicationAddServiceModel applicationToBeAdded) {
        ApplicationEntity application = mapFromApplicationServiceModel(applicationToBeAdded);
        applicationRepository.save(application);
    }

    private ApplicationEntity mapFromApplicationServiceModel(ApplicationAddServiceModel applicationToBeAdded) {
        ApplicationEntity application = new ApplicationEntity();

        application.setApplicant(userRepository.findByUsername(applicationToBeAdded.getAuthor()).orElseThrow());
        application.setJobPublication(jobPublicationRepository.findById(applicationToBeAdded.getProjectId()).orElseThrow());
        application.setTextContent(applicationToBeAdded.getTextContent());
        application.setSubmittedOn(LocalDateTime.now());

        return application;
    }
}
