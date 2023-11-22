package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.binding.UserRegisterBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity fromBindingModel(UserRegisterBindingModel userRegisterBindingModel);
}
