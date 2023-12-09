package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.*;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.WorkInProgressViewModel;
import com.example.archvizarena.repository.JobPublicationRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.WorkInProgressRepository;
import com.example.archvizarena.service.JobService;
import com.example.archvizarena.service.WorkInProgressService;
import com.example.archvizarena.service.exception.ObjectNotFoundException;
import com.example.archvizarena.testUtils.UnitTestUtil;
import com.example.archvizarena.util.mapper.JobPublicationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WorkInProgressServiceImplTest {

    private WorkInProgressService workInProgressServiceToTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JobPublicationRepository jobPublicationRepository;
    @Mock
    private WorkInProgressRepository workInProgressRepository;
    @Mock
    private JobPublicationMapper jobPublicationMapper;
    @Mock
    private JobService jobService;

    private UserEntity artist;

    private UserEntity buyer;
    private JobPublicationEntity jobPublication;
    private MessageEntity message;

    private WorkInProgressEntity workInProgress;
    @Mock
    ArchVizArenaUserDetails userDetails;

    @BeforeEach
    void setUp() {
        workInProgressServiceToTest = new WorkInProgressServiceImpl(
                userRepository,
                jobPublicationRepository,
                workInProgressRepository,
                jobPublicationMapper,
                jobService
        );
        artist = createArtistEntity();
        buyer = createBuyerEntity();
        jobPublication = createJobPublication();
        message = createMessage();
        message.setCommentAuthor(artist);
        workInProgress = createWorkInProgress();
        jobPublication.setBuyer(buyer);
        jobPublication.setApplications(new ArrayList<>());


    }

    @Test
    void testWorkInProgressNotFound() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> workInProgressServiceToTest.findById(2L));
    }

    @Test
    void testGetAllWorkInProgressForAnArtist_ShouldReturn_1() {
        workInProgress.setArtist(artist);
        workInProgress.setBuyer(buyer);
        workInProgress.setMessages(List.of(message));
        when(workInProgressRepository.findAll()).thenReturn(List.of(workInProgress));

        List<WorkInProgressViewModel> allArtistWorkInProgress = workInProgressServiceToTest.getAllArtistWorkInProgress(artist.getId());

        Assertions.assertEquals(1, allArtistWorkInProgress.size());
        Assertions.assertEquals(artist.getName(), allArtistWorkInProgress.get(0).getArtistName());

    }

    @Test
    void testGetAllWorkInProgressForAnArtist_ShouldReturn_EmptyList() {
        UserEntity artist2 = createArtistEntity();
        artist2.setId(2L);
        workInProgress.setArtist(artist2);
        workInProgress.setBuyer(buyer);
        workInProgress.setMessages(List.of(message));
        when(workInProgressRepository.findAll()).thenReturn(List.of(workInProgress));

        List<WorkInProgressViewModel> allArtistWorkInProgress = workInProgressServiceToTest.getAllArtistWorkInProgress(artist.getId());

        Assertions.assertEquals(0, allArtistWorkInProgress.size());


    }

    @Test
    void testAddWorkInProgress() {

        workInProgress.setMessages(List.of(message));
        when(userRepository.findById(buyer.getId())).thenReturn(Optional.of(buyer));
        when(userRepository.findById(artist.getId())).thenReturn(Optional.of(artist));
        when(jobPublicationRepository.findById(jobPublication.getId())).thenReturn(Optional.of(jobPublication));
        doNothing().when(jobService).deactivateJob(jobPublication.getId());

        workInProgressServiceToTest.addWorkInProgress(jobPublication.getId(), artist.getId(), buyer.getId());

        verify(userRepository, times(1)).findById(buyer.getId());
        verify(userRepository, times(1)).findById(artist.getId());
        verify(jobPublicationRepository, times(1)).findById(jobPublication.getId());
        verify(workInProgressRepository, times(1)).save(any(WorkInProgressEntity.class));
        verify(jobService, times(1)).deactivateJob(jobPublication.getId());


    }
    @Test
    void testGetAllWorkInProgressForAnBuyer_ShouldReturn_1() {
        workInProgress.setArtist(artist);
        workInProgress.setBuyer(buyer);
        workInProgress.setMessages(List.of(message));
        when(workInProgressRepository.findAll()).thenReturn(List.of(workInProgress));

        List<WorkInProgressViewModel> allArtistWorkInProgress = workInProgressServiceToTest.getAllBuyerWorkInProgress(buyer.getId());

        Assertions.assertEquals(1, allArtistWorkInProgress.size());
        Assertions.assertEquals(artist.getName(), allArtistWorkInProgress.get(0).getArtistName());

    }

    @Test
    void testGetAllWorkInProgressForAnBuyer_ShouldReturn_EmptyList() {
        UserEntity buyer2 = createBuyerEntity();
        buyer2.setId(2L);
        workInProgress.setArtist(artist);
        workInProgress.setBuyer(buyer2);
        workInProgress.setMessages(List.of(message));
        when(workInProgressRepository.findAll()).thenReturn(List.of(workInProgress));

        List<WorkInProgressViewModel> allArtistWorkInProgress = workInProgressServiceToTest.getAllBuyerWorkInProgress(artist.getId());

        Assertions.assertEquals(0, allArtistWorkInProgress.size());

    }

    @Test
    void testIsViewerAParticipant_should_Be_FalseWhen_PrincipalIdIs_NotInTheWorkInProgress(){
        workInProgress.setBuyer(buyer);
        workInProgress.setArtist(artist);
        UserEntity foreignViewer=createArtistEntity();
        foreignViewer.setId(3L);
        when(userDetails.getUsername()).thenReturn(foreignViewer.getUsername());
        when(userRepository.findByUsername(foreignViewer.getUsername())).thenReturn(Optional.of(foreignViewer));
        when(workInProgressRepository.findById(workInProgress.getId())).thenReturn(Optional.of(workInProgress));

        assertFalse(workInProgressServiceToTest.isViewerAParticipant(workInProgress.getId(),userDetails));
    }



    @Test
    void testIsViewerAParticipant_should_Be_TrueWhen_PrincipalIdIs_InTheWorkInProgress(){
        workInProgress.setBuyer(buyer);
        workInProgress.setArtist(artist);

        when(userDetails.getUsername()).thenReturn(artist.getUsername());
        when(userRepository.findByUsername(artist.getUsername())).thenReturn(Optional.of(artist));
        when(workInProgressRepository.findById(workInProgress.getId())).thenReturn(Optional.of(workInProgress));

        assertTrue(workInProgressServiceToTest.isViewerAParticipant(workInProgress.getId(),userDetails));
    }

}