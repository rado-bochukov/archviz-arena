package com.example.archvizarena.service;

import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.UserProfileViewModel;
import com.example.archvizarena.model.view.ArtistViewModel;
import com.example.archvizarena.model.view.CurrentApplicantViewModel;

import java.util.List;

public interface UserService {
    public void init();

    void register(UserRegisterServiceModel userToBeRegistered);

    List<ArtistViewModel> findAllArtists();

    CurrentApplicantViewModel findCurrentApplicantInfo(String username);

    Long getPrincipalId(String username);

    UserProfileViewModel findUserById(Long id, ArchVizArenaUserDetails userDetails);

    boolean isViewerTheOwner(Long profileId, ArchVizArenaUserDetails viewer);
}
