package com.example.microserver.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.microserver.RootResourceConfig;
import com.example.microserver.exception.UserNotFoundException;
import com.example.microserver.model.User;

import lombok.Synchronized;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    @Inject
    RootResourceConfig resourceConfig;

    @GET
    public List<User> getUsers() {
        return new ArrayList<User>(this.resourceConfig.getData().values());
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") final int id) {
        log.debug("get is called: " + String.valueOf(id));
        val user = this.resourceConfig.getData().get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Synchronized("rwLock")
    public User createUser(User param) {
        log.debug("create is called: " + param.toString());
        val data = this.resourceConfig.getData();
        val id = data.keySet().stream().mapToInt(Integer::intValue).max().orElse(1000) + 1;
        val user = User.of(id, param.getName(), param.getBirthDay());
        data.put(id, user);
        return user;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Synchronized("rwLock")
    public User updateUserById(@PathParam("id") int id, User param) {
        log.debug("update is called: " + String.valueOf(id) + ", " + param.toString());
        val data = this.resourceConfig.getData();
        val origUser = data.get(id);
        if (origUser == null) {
            throw new UserNotFoundException(id);
        }
        val user = User.of(id, param.getName(), param.getBirthDay());
        data.put(id, user);
        return user;
    }

    @DELETE
    @Path("{id}")
    @Synchronized("rwLock")
    public User deleteUserById(@PathParam("id") int id) {
        log.debug("delete is called: " + String.valueOf(id));
        val data = this.resourceConfig.getData();
        val user = data.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        data.remove(id);
        return user;
    }
}
