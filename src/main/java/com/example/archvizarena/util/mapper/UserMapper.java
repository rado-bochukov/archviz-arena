package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.binding.UserEditBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.ArtistViewModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper  {



    public UserMapper() {

    }

    public ArtistViewModel mapToArtistViewModel(UserEntity userEntity) {
        ArtistViewModel artistViewModel = new ArtistViewModel();

        artistViewModel.setId(userEntity.getId());
        artistViewModel.setName(userEntity.getName());
        artistViewModel.setCountry(userEntity.getCountry());
        artistViewModel.setCreatorTypeEnum(userEntity.getCreatorType());
        artistViewModel.setPricePerImage(userEntity.getPricePerImage());
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
