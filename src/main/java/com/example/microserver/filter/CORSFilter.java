package com.example.microserver.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class CORSFilter implements ContainerResponseFilter {

    private final Map<String, String> header = new HashMap<String, String>() {
        {
            put("Access-Control-Allow-Origin", "*");
            put("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            put("Access-Control-Allow-Credentials", "true");
            put("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    };

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        log.debug("CORSFilter.filter is called");

        for (val e : this.header.entrySet()) {
            response.getHeaders().add(e.getKey(), e.getValue());
        }
    }
}