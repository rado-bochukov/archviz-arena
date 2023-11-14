package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.Picture;
import com.example.archvizarena.repository.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void initPictures() {
        if (pictureRepository.findAll().isEmpty()) {
            Picture profileImage = new Picture();
            profileImage.setUrl("/img/user-avatar.svg");
            pictureRepository.save(profileImage);
        }


    }
}
