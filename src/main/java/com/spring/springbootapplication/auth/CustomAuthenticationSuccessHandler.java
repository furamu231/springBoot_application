package com.spring.springbootapplication.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.spring.springbootapplication.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
    if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
        String identifier = customUserDetails.getUsername();

        Integer userId = userService.findByUserNameOrEmail(identifier);
        
        if (userId != null) {
            response.sendRedirect("/users/success/" + userId);
        } else {
            response.sendRedirect("/users/signin?error=true");
            System.err.println("User ID is null for identifier: " + identifier);
        }
    } else {
        response.sendRedirect("/users/signin?error=true");
        System.err.println("Principal is not an instance of CustomUserDetails.");
    }
}
}