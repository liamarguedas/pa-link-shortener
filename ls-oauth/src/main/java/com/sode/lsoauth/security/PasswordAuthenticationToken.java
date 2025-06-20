package com.sode.lsoauth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;
    private final String password;

    public PasswordAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
