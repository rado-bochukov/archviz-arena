package com.example.archvizarena.service;

import com.example.archvizarena.model.binding.UserRegisterBindingModel;
import com.example.archvizarena.model.service.UserRegisterServiceModel;

public interface UserService {
    public void init();

    void register(UserRegisterServiceModel userToBeRegistered);

}
