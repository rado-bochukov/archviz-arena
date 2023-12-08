package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ArchVizArenaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public ArchVizArenaUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    private UserDetails map(UserEntity user) {

        List<String> userRoles = user.getRoles().stream().map(r -> r.getRole().name()).collect(Collectors.toList());

        if(user.getUserOccupation()!=null){
        userRoles.add(user.getUserOccupation().name());
        }
         return new ArchVizArenaUserDetails(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                userRoles
                        .stream()
                        .map(this::mapRole)
                        .toList());
    }


    private GrantedAuthority mapRole(String userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole);
    }
}
