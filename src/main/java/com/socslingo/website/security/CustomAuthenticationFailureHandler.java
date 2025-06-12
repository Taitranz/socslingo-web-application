package com.socslingo.website.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
        
        // Get the username that was submitted
        String username = request.getParameter("username");
        
        // Store it in the session so we can retrieve it on the login page
        if (username != null && !username.trim().isEmpty()) {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", username);
        }
        
        // Redirect to login page with error parameter
        response.sendRedirect("/login?error=true");
    }
}
