package com.example.archvizarena.service.impl;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.service.PictureService;
import com.example.archvizarena.testUtils.UnitTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {

    private PictureService pictureServiceToTest;
    @Mock
    private PictureRepository pictureRepository;

    @BeforeEach
    void setUp(){
        pictureServiceToTest=new PictureServiceImpl(pictureRepository);
    }

    @Test
    void testInitPictures(){
        PictureEntity testPicture= UnitTestUtil.createPicture();
        testPicture.setUrl("/images/default_avatar.jpg");

        pictureServiceToTest.initPictures();

        verify(pictureRepository, times(1)).save(any(PictureEntity.class));

    }
    @Test
    void testInitPictures_WithNotEmptyPictureRepository(){
        PictureEntity testPicture= UnitTestUtil.createPicture();
        testPicture.setUrl("/images/default_avatar.jpg");

        when(pictureRepository.findAll()).thenReturn(List.of(testPicture));

        pictureServiceToTest.initPictures();

        verify(pictureRepository, times(0)).save(any(PictureEntity.class));

    }

    @Test
    void testSavePicture(){
        PictureEntity testPicture= UnitTestUtil.createPicture();

        pictureServiceToTest.savePicture(testPicture.getUrl());

        verify(pictureRepository, times(1)).save(any(PictureEntity.class));

    }


}