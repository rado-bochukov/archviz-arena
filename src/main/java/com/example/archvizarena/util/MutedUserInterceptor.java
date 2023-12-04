package com.example.archvizarena.util;

import com.example.archvizarena.model.user.ArchVizArenaUserDetails;
import com.example.archvizarena.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;

public class MutedUserInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public MutedUserInterceptor(UserService userService) {

        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            ArchVizArenaUserDetails principal = (ArchVizArenaUserDetails) authentication.getPrincipal();

            if (userService.isUserMuted(principal.getUsername())) {
                response.sendRedirect("/comments/add/error");
                return false;
            }
        }
        return true;
    }
}
