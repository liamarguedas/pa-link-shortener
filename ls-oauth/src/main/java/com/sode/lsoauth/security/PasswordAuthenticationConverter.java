package com.sode.lsoauth.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class PasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {

        if( !"password".equals(request.getParameter("grant_type"))) {
            return null;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
