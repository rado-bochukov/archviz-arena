package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import com.example.archvizarena.testUtils.UnitTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.example.archvizarena.testUtils.UnitTestUtil.createBuyerEntity;
import static com.example.archvizarena.testUtils.UnitTestUtil.createJobPublication;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class JobPublicationMapperTest {

    private JobPublicationMapper mapperToTest;

    private JobPublicationEntity jobPublication;
    private UserEntity buyer;


    @BeforeEach
    void setUp(){
        mapperToTest=new JobPublicationMapper();
        jobPublication= createJobPublication();
        buyer=createBuyerEntity();
        jobPublication.setBuyer(buyer);
        jobPublication.setApplications(new ArrayList<>());
    }

    @Test
    void  testMapping(){

        JobPublicationViewModel jobPublicationViewModel = mapperToTest.mapToJobViewModel(jobPublication);

        assertEquals(jobPublication.getId(),jobPublicationViewModel.getId());
        assertEquals(jobPublication.getDescription(),jobPublicationViewModel.getDescription());
        assertEquals(jobPublication.getBudget(),jobPublicationViewModel.getBudget());
        assertEquals(jobPublication.getBuyer().getName(),jobPublicationViewModel.getAuthorName());
        assertEquals(jobPublication.getBuyer().getCountry(),jobPublicationViewModel.getAuthorCountry());
    }

}