package com.example.archvizarena.web;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTestIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testRegistration() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("name", "Artist 1")
                                .param("email", "artist1@artists.com")
                                .param("username", "artist1")
                                .param("password", "artist1")
                                .param("country", "Bulgaria")
                                .param("description", "I am artist 1")
                                .param("userOccupation", "ARTIST")
                                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login"));

    }

    @Test
    void testRegistration_validation_fail() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("name", "Artist 1")
                                .param("email", "")
                                .param("username", "artist1")
                                .param("password", "artist1")
                                .param("description", "I am artist 1")
                                .param("userOccupation", "ARTIST")
                                .param("creatorType", "FREELANCER")
                                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

}