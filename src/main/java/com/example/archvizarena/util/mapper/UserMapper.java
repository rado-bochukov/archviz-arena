package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.ArtistViewModel;

import java.util.Optional;

public interface UserMapper {
    ArtistViewModel mapToArtistViewModel(UserEntity userEntity);

    UserEditBindingModel mapFromUserEntity(UserEntity byId);

    void mapUserFromUserEditModel(UserEntity user, UserEditBindingModel userEditBindingModel);

}
