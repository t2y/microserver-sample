package com.example.microserver.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.microserver.model.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    public List<Book> getBooks() {
        return new ArrayList<Book>() {
            {
                add(Book.of(123, "サンプル1", LocalDate.parse("2018-12-01")));
                add(Book.of(456, "サンプル2", LocalDate.parse("2018-12-02")));
            }
        };
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") final int id) {
        log.debug("get is called: " + String.valueOf(id));
        return Book.of(123, "サンプル1", LocalDate.parse("2018-12-01"));
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Book createBook(Book param) {
        log.debug("create is called: " + param.toString());
        return Book.of(456, "サンプル2", LocalDate.parse("2018-12-02"));
    }

}
