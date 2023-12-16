package com.example.archvizarena.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "archvizarenacloud",
                "api_key", "282973862892432",
                "api_secret", "y6rDDd7U0quFhfthq1b6WvWmmLI"));
    }
}
