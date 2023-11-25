package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.WorkInProgressEntity;
import com.example.archvizarena.model.view.WorkInProgressViewModel;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.WorkInProgressRepository;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkInProgressServiceImpl implements WorkInProgressService {

    private final UserRepository userRepository;
    private final JobPublicationRepository jobPublicationRepository;
    private final WorkInProgressRepository workInProgressRepository;

    private final JobPublicationMapper jobPublicationMapper;

    private final JobService jobService;

    public WorkInProgressServiceImpl(UserRepository userRepository, JobPublicationRepository jobPublicationRepository, WorkInProgressRepository workInProgressRepository, JobPublicationMapper jobPublicationMapper, JobService jobService) {
        this.userRepository = userRepository;
        this.jobPublicationRepository = jobPublicationRepository;
        this.workInProgressRepository = workInProgressRepository;
        this.jobPublicationMapper = jobPublicationMapper;
        this.jobService = jobService;
    }

    @Override
    public void addWorkInProgress(Long jobId, Long applicantId, Long principalId) {
        WorkInProgressEntity workInProgress=new WorkInProgressEntity();

        UserEntity buyer=userRepository.findById(principalId).orElseThrow();
        UserEntity artist=userRepository.findById(applicantId).orElseThrow();
        JobPublicationEntity job=jobPublicationRepository.findById(jobId).orElseThrow();

        workInProgress.setArtist(artist);
        workInProgress.setBuyer(buyer);
        workInProgress.setJob(job);

        workInProgressRepository.save(workInProgress);
        jobService.deactivateJob(jobId,principalId);

    }

    @Override
    public List<WorkInProgressViewModel> getAllArtistWorkInProgress(Long id) {
        List<WorkInProgressViewModel> collect = workInProgressRepository.findAll()
                .stream().filter(w -> w.getArtist().getId().equals(id))
                .map(this::toWorkInProgressViewModel)
                .toList();

        return collect;
    }

    @Override
    public List<WorkInProgressViewModel> getAllBuyerWorkInProgress(Long id) {
        List<WorkInProgressViewModel> collect = workInProgressRepository.findAll()
                .stream().filter(w -> w.getBuyer().getId().equals(id))
                .map(this::toWorkInProgressViewModel)
                .toList();

        return collect;
    }


    private WorkInProgressViewModel toWorkInProgressViewModel(WorkInProgressEntity workInProgressEntity) {
        WorkInProgressViewModel workInProgressViewModel=new WorkInProgressViewModel();
        workInProgressViewModel.setArtistName(workInProgressEntity.getArtist().getName());
        workInProgressViewModel.setBuyerName(workInProgressEntity.getBuyer().getName());
        workInProgressViewModel.setJob(jobPublicationMapper.mapToJobViewModel(workInProgressEntity.getJob()));
        workInProgressViewModel.setId(workInProgressEntity.getId());

        return workInProgressViewModel;

    }
}
