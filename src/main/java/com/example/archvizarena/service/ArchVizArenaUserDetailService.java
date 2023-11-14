package com.example.archvizarena.service;

import com.example.archvizarena.model.entity.User;
import com.example.archvizarena.model.entity.UserRole;
import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ArchVizArenaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public ArchVizArenaUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Този метод представлява мапване на нашето ентити към репрезентация на User която Спринг разбира и ползва
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    private UserDetails map(User user) {

        List<String> userRoles = user.getRoles().stream().map(r -> r.getRole().name()).collect(Collectors.toList());
        userRoles.add(user.getUserOccupation().name());

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
//        използва се за мапването на ролите
//        винаги трябва да има префикс "ROLE_" за да може да се изполва в последствие за проверка на ролята с метода hasRole()
        return new SimpleGrantedAuthority("ROLE_" + userRole);
    }
}
