package com.example.archvizarena.config;

import com.example.archvizarena.service.UserService;
import com.example.archvizarena.util.MutedUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserService userService;

    public WebMvcConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MutedUserInterceptor(userService))
                .addPathPatterns("/projects/details/{id}/comments/add");
    }
}
