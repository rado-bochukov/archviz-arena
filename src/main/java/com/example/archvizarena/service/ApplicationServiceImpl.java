package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.ApplicationEntity;
import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.model.view.ApplicationViewModel;
import com.example.archvizarena.model.view.ArtistViewModel;
import com.example.archvizarena.repository.ApplicationRepository;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPublicationRepository jobPublicationRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  JobPublicationRepository jobPublicationRepository,
                                  UserRepository userRepository,
                                  UserMapper userMapper) {
        this.applicationRepository = applicationRepository;
        this.jobPublicationRepository = jobPublicationRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void saveApplication(ApplicationAddServiceModel applicationToBeAdded) {
        ApplicationEntity application = mapFromApplicationServiceModel(applicationToBeAdded);
        applicationRepository.save(application);
    }

    @Override
    public List<ApplicationViewModel> findJobApplications(Long id) {

        return applicationRepository.findByJobPublication_Id(id)
                .stream()
                .map(this::mapToApplicationViewModel)
                .collect(Collectors.toList());
    }

    private ApplicationViewModel mapToApplicationViewModel(ApplicationEntity application) {

        ApplicationViewModel applicationViewModel=new ApplicationViewModel();
        ArtistViewModel artist= userMapper.mapToArtistViewModel( application.getApplicant());
        applicationViewModel.setApplicant(artist);
        applicationViewModel.setId(application.getId());
        applicationViewModel.setSubmittedOn(application.getSubmittedOn());
        applicationViewModel.setTextContent(application.getTextContent());
        return applicationViewModel;
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
