package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.ArtistViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    private final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ArtistViewModel mapToArtistViewModel(UserEntity userEntity) {
        ArtistViewModel artistViewModel = modelMapper.map(userEntity, ArtistViewModel.class);
        if (userEntity.getProfilePicture() != null) {
            String profilePicUrl = userEntity.getProfilePicture().getUrl();
            artistViewModel.setPictureUrl(profilePicUrl);
        }
        return artistViewModel;
    }
}
