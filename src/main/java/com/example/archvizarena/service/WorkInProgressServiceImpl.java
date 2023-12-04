package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.MessageViewModel;
import com.example.archvizarena.model.view.WorkInProgressViewModel;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.WorkInProgressRepository;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        WorkInProgressEntity workInProgress = new WorkInProgressEntity();

        UserEntity buyer = userRepository.findById(principalId).orElseThrow();
        UserEntity artist = userRepository.findById(applicantId).orElseThrow();
        JobPublicationEntity job = jobPublicationRepository.findById(jobId).orElseThrow();

        workInProgress.setArtist(artist);
        workInProgress.setBuyer(buyer);
        workInProgress.setJob(job);

        workInProgressRepository.save(workInProgress);
        jobService.deactivateJob(jobId);

    }

    @Override
    public List<WorkInProgressViewModel> getAllArtistWorkInProgress(Long id) {
        List<WorkInProgressViewModel> collect = workInProgressRepository.findAll()
                .stream().filter(w -> w.getArtist().getId().equals(id))
                .map(this::fromWorkInProgressEntity)
                .toList();

        return collect;
    }

    @Override
    public List<WorkInProgressViewModel> getAllBuyerWorkInProgress(Long id) {
        List<WorkInProgressViewModel> collect = workInProgressRepository.findAll()
                .stream().filter(w -> w.getBuyer().getId().equals(id))
                .map(this::fromWorkInProgressEntity)
                .toList();

        return collect;
    }

    @Override
    public WorkInProgressViewModel findById(Long id) {

        WorkInProgressEntity byId = workInProgressRepository.findById(id).orElseThrow();
        return this.fromWorkInProgressEntity(byId);
    }

    @Override
    public boolean isViewerAParticipant(Long workInProgressID, ArchVizArenaUserDetails viewer) {
        UserEntity user = userRepository.findByUsername(viewer.getUsername()).orElse(null);
        if (user == null) {
            return false;
        }
        WorkInProgressEntity workInProgress = workInProgressRepository.findById(workInProgressID).orElseThrow();

        return workInProgress.getBuyer().getId().equals(user.getId())
                || workInProgress.getArtist().getId().equals(user.getId());

    }


    private WorkInProgressViewModel fromWorkInProgressEntity(WorkInProgressEntity workInProgressEntity) {
        WorkInProgressViewModel workInProgressViewModel = new WorkInProgressViewModel();
        workInProgressViewModel.setArtistName(workInProgressEntity.getArtist().getName());
        workInProgressViewModel.setBuyerName(workInProgressEntity.getBuyer().getName());
        workInProgressViewModel.setJob(jobPublicationMapper.mapToJobViewModel(workInProgressEntity.getJob()));
        workInProgressViewModel.setId(workInProgressEntity.getId());
        workInProgressViewModel.setMessages(workInProgressEntity.getMessages().stream()
                .map(this::mapFromEntity).collect(Collectors.toList()));

        return workInProgressViewModel;
    }

    private MessageViewModel mapFromEntity(MessageEntity message) {
        MessageViewModel messageViewModel = new MessageViewModel();

        messageViewModel.setAuthorName(message.getCommentAuthor().getName());
        messageViewModel.setCreated(message.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss" )));
        messageViewModel.setTextContent(message.getTextContent());

        return messageViewModel;
    }
}
