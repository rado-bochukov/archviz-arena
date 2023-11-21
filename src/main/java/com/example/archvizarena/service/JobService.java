package com.example.archvizarena.service;

import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.view.JobPublicationBrowseViewModel;

import java.util.List;

public interface JobService {
    List<JobPublicationBrowseViewModel> findAllJobs();


    void addJob(JobPublicationAddServiceModel jobToBeAdded, String username);
}
