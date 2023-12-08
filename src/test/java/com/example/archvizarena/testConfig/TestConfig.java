package com.example.archvizarena.testConfig;

import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.ArchVizArenaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {


    @Bean
    public ArchVizArenaUserDetailService ava(UserRepository userRepository){
        return new ArchVizArenaUserDetailService(userRepository);
    }
}
