package com.example.microserver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.example.microserver.exception.UserNotFoundExceptionMapper;
import com.example.microserver.filter.AuthFilter;
import com.example.microserver.filter.CORSFilter;
import com.example.microserver.model.BookBinder;
import com.example.microserver.model.User;
import com.example.microserver.model.UserBinder;
import com.example.microserver.resource.BookResource;
import com.example.microserver.resource.UserResource;

import lombok.Getter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/")
public class RootResourceConfig extends ResourceConfig {

    private static final String DATA_PATH = "data.db";

    @Getter
    private final Map<Integer, User> data = new HashMap<>();

    private void initData() throws IOException, URISyntaxException {
        val url = this.getClass().getClassLoader().getResource(DATA_PATH);
        val path = Paths.get(url.toURI());
        for (val line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
            val values = line.split(",");
            val id = Integer.valueOf(values[0]);
            val user = User.of(id, values[1], LocalDate.parse(values[2]));
            log.debug(user.toString());
            this.data.put(id, user);
        }
    }

    public RootResourceConfig() throws IOException, URISyntaxException {
        log.debug("initialize RootResourceConfig");
        this.initData();

        // common
        register(ObjectMapperProvider.class);
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
        register(CORSFilter.class);

        // user rsource
        register(new UserBinder());
        register(UserResource.class);
        register(UserNotFoundExceptionMapper.class);

        // book resource
        register(new BookBinder());
        register(BookResource.class);
    }
}
