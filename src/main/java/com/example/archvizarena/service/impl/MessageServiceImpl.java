package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.MessageEntity;
import com.example.archvizarena.model.service.MessageAddServiceModel;
import com.example.archvizarena.repository.MessageRepository;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.repository.WorkInProgressRepository;
import com.example.archvizarena.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final WorkInProgressRepository workInProgressRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, WorkInProgressRepository workInProgressRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.workInProgressRepository = workInProgressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveAndAddMessage(MessageAddServiceModel messageToBeAdded) {
        MessageEntity message=new MessageEntity();

        message.setTextContent(messageToBeAdded.getTextContent());
        message.setWorkInProgress(workInProgressRepository.findById(messageToBeAdded.getWorkInProgressId()).orElseThrow());
        message.setCommentAuthor(userRepository.findByUsername(messageToBeAdded.getAuthorUserName()).orElseThrow());
        message.setCreated(LocalDateTime.now());
        messageRepository.save(message);

    }
}
