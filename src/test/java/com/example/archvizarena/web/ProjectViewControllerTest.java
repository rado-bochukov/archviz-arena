package com.example.archvizarena.web;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.model.view.ProjectDetailsViewModel;
import com.example.archvizarena.service.ProjectService;
import com.example.archvizarena.testConfig.TestConfig;
import com.example.archvizarena.testUtils.TestDataUtil;
import com.example.archvizarena.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfig.class)
class ProjectViewControllerTest {

    private static final String TEST_ARTIST_USERNAME = "artist1";

    @Autowired
    UserTestDataUtil userTestDataUtil;

    @Autowired
    TestDataUtil testDataUtil;

    @Autowired
    ProjectService projectService;


    @Autowired
    private MockMvc mockMvc;

    @Mock
    ArchVizArenaUserDetails archVizArenaUserDetails;

    private UserEntity owner;


    @BeforeEach
    void setUp() {
        testDataUtil.cleanUp();
        userTestDataUtil.cleanUp();
        owner= userTestDataUtil.createArtist(TEST_ARTIST_USERNAME);
    }

    @AfterEach
    void clearUp() {
        testDataUtil.cleanUp();
        userTestDataUtil.cleanUp();
    }

    @Test
    @WithAnonymousUser
    void TestAnonymousUserGetAllProjects() throws Exception {

        mockMvc.perform(
                        get("/projects/all")
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful())
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("allProjects"))
                .andExpect(model().attributeExists("projectsCount"))
                .andExpect(model().attributeExists("commentToBeAdded"))
                .andExpect(model().attributeExists("projectSearchBindingModel"))
        ;


    }


    @Test
    void TestGetProjectDetail_shouldReturnNotFoundPage() throws Exception {
        mockMvc.perform(
                        get("/projects/details/5")
                                .with(csrf())
                ).andExpect(view().name("not-found-exception"));
    }

    @Test
    @WithUserDetails(
            value = TEST_ARTIST_USERNAME,
            userDetailsServiceBeanName = "ava",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void TestGetProjectDetail_successful() throws Exception {

        when(archVizArenaUserDetails.getUsername()).thenReturn(TEST_ARTIST_USERNAME);
        PortfolioProjectEntity project=testDataUtil.createTestProject(owner);
        mockMvc.perform(
                get("/projects/details/1")
                        .with(csrf()))
                .andExpect(view().name("project-detail"))
                .andExpect(model().attributeExists("project"))
        ;

        ProjectDetailsViewModel returnedProject = projectService.findById(1L, archVizArenaUserDetails);

        Assertions.assertEquals(1,returnedProject.getId());
        Assertions.assertEquals(owner.getName(),returnedProject.getAuthorName());
    }






}