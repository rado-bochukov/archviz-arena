package com.example.archvizarena.web;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.view.PictureUploadViewModel;
import com.example.archvizarena.testConfig.TestConfig;
import com.example.archvizarena.testUtils.TestDataUtil;
import com.example.archvizarena.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfig.class)
class ProjectUploadControllerTest {

    private static final String TEST_ARTIST_USERNAME = "artist1";
    private static final String TEST_BUYER_USERNAME = "buyer1";
    private static final String TEST_ADMIN_USERNAME = "admin";

    @Autowired
    UserTestDataUtil userTestDataUtil;

    @Autowired
    TestDataUtil testDataUtil;


    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        testDataUtil.cleanUp();
        userTestDataUtil.cleanUp();
        UserEntity artist = userTestDataUtil.createArtist(TEST_ARTIST_USERNAME);
    }

    @AfterEach
    void clearUp() {
        testDataUtil.cleanUp();
        userTestDataUtil.cleanUp();
    }

    @Test
    @WithAnonymousUser
    void TestAnonymousUserUploadProjectFails() throws Exception {

        mockMvc.perform(
                        get("/projects/add")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(
            value = TEST_ARTIST_USERNAME,
            userDetailsServiceBeanName = "ava",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void TestUserUploadProject() throws Exception {

        List<PictureEntity> pictures = testDataUtil.createTestPictures();
        mockMvc.perform(MockMvcRequestBuilders.post("/projects/add")
                        .param("title", "Project 2")
                        .param("description", "This is project 2")
                        .param("category", "EXTERIOR")
                        .param("picturesUrl", String.valueOf(List.of(pictures.get(0).getUrl(), pictures.get(1).getUrl())))
                        .with(csrf())

                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithUserDetails(
            value = TEST_ARTIST_USERNAME,
            userDetailsServiceBeanName = "ava",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void TestUserUploadProjectValidationFails() throws Exception {

        List<PictureEntity> pictures = testDataUtil.createTestPictures();
        mockMvc.perform(MockMvcRequestBuilders.post("/projects/add")
                        .param("title", "")
                        .param("description", "This is project 2")
                        .param("category", "EXTERIOR")
                        .param("picturesUrl", String.valueOf(List.of(pictures.get(0).getUrl(), pictures.get(1).getUrl())))
                        .with(csrf())

                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/add"));
    }

    @Test
    @WithUserDetails(
            value = TEST_ARTIST_USERNAME,
            userDetailsServiceBeanName = "ava",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void TestUserGetUploadProject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/add")
                        .with(csrf())
                ).andExpect(model().attributeExists("links"))
                .andExpect(model().attributeExists("uploadedPictures"));
    }

}