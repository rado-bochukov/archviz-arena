package com.example.archvizarena.model.user;

import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ArchVizArenaUserDetails implements UserDetails {
    private final String name;
    private final String email;
    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> authorities;


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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean isArtist(){
        for (GrantedAuthority authority : this.authorities) {
            if (authority.getAuthority().equals("ROLE_ARTIST")){
                return true;
            }
        }
        return false;
    }

    public boolean isBuyer(){
        for (GrantedAuthority authority : this.authorities) {
            if (authority.getAuthority().equals("ROLE_BUYER")){
                return true;
            }
        }
        return false;
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
