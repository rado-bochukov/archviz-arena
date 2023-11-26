package com.example.archvizarena.config;

import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import com.example.archvizarena.repository.UserRepository;
import com.example.archvizarena.service.ArchVizArenaUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll().
                                        requestMatchers("/", "/users/login", "/users/register",
                                                "/users/login-error",
                                                "/artists/all", "/users/details/{id}",
                                                "/projects/all", "/projects/details/{id}",
                                                "jobs/all", "jobs/details/{id}")
                                        .permitAll().
                                        requestMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name()).
                                        requestMatchers("/pages/admins").hasRole(UserRoleEnum.ADMIN.name()).
                                        requestMatchers("/jobs/add").hasRole(UserOccupationEnum.BUYER.name()).
                                        requestMatchers("/jobs/job-applicants/{id}").hasRole(UserOccupationEnum.BUYER.name()).
                                        requestMatchers("/users/myProfile").hasAnyRole(UserOccupationEnum.ARTIST.name(),UserOccupationEnum.BUYER.name()).
                                        requestMatchers("/projects/add",
                                                "/jobs/details/application/add/{id}").hasRole(UserOccupationEnum.ARTIST.name()).
                                        anyRequest().authenticated()
                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/users/login").
                                        usernameParameter(
                                                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                        passwordParameter(
                                                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                        defaultSuccessUrl("/").
                                        failureForwardUrl("/users/login-error")
                )
                .logout((logout) ->
                        logout.logoutUrl("/users/logout").
                                logoutSuccessUrl("/").//go to homepage after logout
                                invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID"));


        return http.build();
    }


    @Bean
    //енкодър за пароли за да не се запазват като плейн текст
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ArchVizArenaUserDetailService(userRepository);
    }

    //Така се справяме с редиректа след логин заради грешка 999
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/resources/static", "/favicon.ico", "/error","/img/**");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
