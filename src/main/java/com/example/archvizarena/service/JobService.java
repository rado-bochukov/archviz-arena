package com.example.archvizarena.service;

import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.JobPublicationViewModel;

import java.util.List;

public interface JobService {
    List<JobPublicationViewModel> findAllJobs();

    void addJob(JobPublicationAddServiceModel jobToBeAdded, String username);

    JobPublicationViewModel findJobById(Long id,ArchVizArenaUserDetails userDetails);

    void deactivateJob(Long id);

    void deleteJob(Long id, Long userId);

    void activateJob(Long id);

    boolean isViewerTheOwner(Long jobId, ArchVizArenaUserDetails viewer);

    void deactivateUserJobPublications(Long id);
}
