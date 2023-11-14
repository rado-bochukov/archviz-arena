package com.example.archvizarena.model.user;

import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ArchVizArenaUserDetails implements UserDetails {
    private final String name;
    private final String email;
    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> authorities;
    private UserOccupationEnum occupation;

    public ArchVizArenaUserDetails(String name,
                                   String email,
                                   String username,
                                   String password,
                                   Collection<GrantedAuthority>authorities) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserOccupationEnum getOccupation() {
        return occupation;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
