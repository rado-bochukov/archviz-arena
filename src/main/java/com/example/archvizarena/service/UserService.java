package com.example.archvizarena.service;

import com.example.archvizarena.model.binding.ArtistSearchBindingModel;
import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.UserProfileViewModel;
import com.example.archvizarena.model.view.ArtistViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public void init();

    void register(UserRegisterServiceModel userToBeRegistered);

    Page<ArtistViewModel> findAllArtists(Pageable pageable);


    Long getPrincipalId(String username);

    UserProfileViewModel findUserById(Long id, ArchVizArenaUserDetails userDetails);

    boolean isViewerTheOwner(Long profileId, ArchVizArenaUserDetails viewer);

    UserEditBindingModel findUserById(Long id);

    void editProfile(UserEditBindingModel userEditBindingModel);

    boolean newUserNameIsUnique(Long id, String username);


    UserProfileViewModel mapFromEntity(UserEntity user);

    void updateThePrincipalAuthenticationToken(String username, ArchVizArenaUserDetails userDetails);

    String getNameById(Long id);

    boolean isUserMuted(String username);


    Page<ArtistViewModel> searchArtists(ArtistSearchBindingModel artistSearchBindingModel,Pageable pageable);

}
