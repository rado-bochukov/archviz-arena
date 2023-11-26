package com.example.archvizarena.service;

import com.example.archvizarena.model.service.MessageAddServiceModel;

public interface MessageService {
    void saveAndAddMessage(MessageAddServiceModel messageToBeAdded);
}
