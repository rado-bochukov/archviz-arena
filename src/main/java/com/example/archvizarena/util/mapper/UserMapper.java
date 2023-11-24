package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.ArtistViewModel;

public interface UserMapper {
    ArtistViewModel mapToArtistViewModel(UserEntity userEntity);
}
