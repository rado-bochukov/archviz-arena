package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.MessageEntity;
import com.example.archvizarena.model.entity.ReportEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.WorkInProgressEntity;
import com.example.archvizarena.model.service.MessageAddServiceModel;
import com.example.archvizarena.repository.MessageRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.WorkInProgressRepository;
import com.example.archvizarena.service.MessageService;
import com.example.archvizarena.testUtils.UnitTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.archvizarena.testUtils.UnitTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    private MessageService messageServiceToTest;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private WorkInProgressRepository workInProgressRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        messageServiceToTest=new MessageServiceImpl(
                messageRepository,
                workInProgressRepository,
                userRepository
        );

    }

    @Test
    void testSaveAndAddMessage(){
        MessageAddServiceModel messageToBeAdded=new MessageAddServiceModel();

        MessageEntity message= createMessage();
        UserEntity author=createArtistEntity();
        WorkInProgressEntity wip=createWorkInProgress();
        messageToBeAdded.setAuthorUserName(author.getUsername());
        messageToBeAdded.setWorkInProgressId(wip.getId());
        messageToBeAdded.setTextContent(message.getTextContent());

        when(workInProgressRepository.findById(messageToBeAdded.getWorkInProgressId())).thenReturn(Optional.of(wip));
        when(userRepository.findByUsername(messageToBeAdded.getAuthorUserName())).thenReturn(Optional.of(author));

        messageServiceToTest.saveAndAddMessage(messageToBeAdded);

        verify(workInProgressRepository, times(1)).findById(wip.getId());
        verify(userRepository, times(1)).findByUsername(author.getUsername());
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }


}