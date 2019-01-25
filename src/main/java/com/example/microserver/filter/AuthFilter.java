package com.example.microserver.filter;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import com.example.microserver.model.AuthContext;
import com.example.microserver.model.AuthUser;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Priority(Priorities.AUTHENTICATION)
@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Context
    UriInfo uriInfo;

    private static final String AUTH_HEADER = "Auth-Token";

    private Optional<AuthUser> validateToken(String token) {
        String name;
        if (token.equals("secret")) {
            name = "admin";
        } else if (token.equals("passwd")) {
            name = "user1";
        } else {
            return Optional.empty();
        }
        return Optional.of(new AuthUser(name));
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.debug("AuthFilter.filter is called");

        val authHeaderVal = requestContext.getHeaderString(AUTH_HEADER);
        val user = validateToken(authHeaderVal);
        if (!user.isPresent()) {
            return;
        }

        requestContext.setSecurityContext(new AuthContext(uriInfo, user.get()));
    }
}
