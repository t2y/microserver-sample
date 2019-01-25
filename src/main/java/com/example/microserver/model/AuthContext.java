package com.example.microserver.model;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AuthContext implements SecurityContext {

    private UriInfo uriInfo;
    private AuthUser user;

    @Override
    public Principal getUserPrincipal() {
        log.debug(user.toString());
        return () -> this.user.getName();
    }

    @Override
    public boolean isUserInRole(String role) {
        log.debug("isUserInRole is called");
        return user.hasRole(role);
    }

    @Override
    public boolean isSecure() {
        log.debug("isSecure is called");
        return uriInfo.getAbsolutePath().toString().startsWith("https");
    }

    @Override
    public String getAuthenticationScheme() {
        return "Token-Based-Auth-Scheme";
    }
}
