package com.example.archvizarena.service;

import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.view.ArtistViewModel;

import java.util.List;

public interface UserService {
    public void init();

    void register(UserRegisterServiceModel userToBeRegistered);

    List<ArtistViewModel> findAllArtists();
}
