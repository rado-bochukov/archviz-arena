package com.example.archvizarena.service;

import com.example.archvizarena.model.binding.JobPublicationSearchBindingModel;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    List<JobPublicationViewModel> findAllJobs();

    void addJob(JobPublicationAddServiceModel jobToBeAdded, String username);

    JobPublicationViewModel findJobById(Long id,ArchVizArenaUserDetails userDetails);

    void deactivateJob(Long id);

    void deleteJob(Long id, Long userId);

    void activateJob(Long id);

    boolean isViewerTheOwner(Long jobId, ArchVizArenaUserDetails viewer);

//    void deactivateUserJobPublications(Long id);

    Page<JobPublicationViewModel> findAllActiveJobs(Pageable pageable);

    Page<JobPublicationViewModel> searchJobPublications(JobPublicationSearchBindingModel jobPublicationSearchBindingModel, Pageable pageable);
}
