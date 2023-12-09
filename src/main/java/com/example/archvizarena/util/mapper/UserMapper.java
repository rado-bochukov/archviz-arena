package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.ArtistViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper  {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ArtistViewModel mapToArtistViewModel(UserEntity userEntity) {
        ArtistViewModel artistViewModel = modelMapper.map(userEntity, ArtistViewModel.class);
        if (userEntity.getProfilePicture() != null) {
            String profilePicUrl = userEntity.getProfilePicture().getUrl();
            artistViewModel.setPictureUrl(profilePicUrl);
        }
        return artistViewModel;
    }


    public UserEditBindingModel mapFromUserEntity(UserEntity userEntity) {
        UserEditBindingModel userEditBindingModel=new UserEditBindingModel();
        userEditBindingModel.setUsername(userEntity.getUsername());
        userEditBindingModel.setEmail(userEntity.getEmail());
        userEditBindingModel.setName(userEntity.getName());
        userEditBindingModel.setDescription(userEntity.getDescription());
        userEditBindingModel.setCountry(userEntity.getCountry());
        userEditBindingModel.setUserOccupation(userEntity.getUserOccupation());
        userEditBindingModel.setPricePerImage(userEntity.getPricePerImage());
        userEditBindingModel.setCreatorType(userEntity.getCreatorType());
        if(userEntity.getProfilePicture()!=null){

        userEditBindingModel.setProfilePicture(userEntity.getProfilePicture().getUrl());
        }
        return userEditBindingModel;
    }

}
