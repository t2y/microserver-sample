package com.example.microserver.model;

import java.time.LocalDate;

import com.example.microserver.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Value;

@Value(staticConstructor = "of")
public class Book {
    public final int isbn;
    public final String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    public final LocalDate published;

    @JsonCreator
    public static Book create(
            @JsonProperty("isbn") int isbn,
            @JsonProperty("name") String name,
            @JsonProperty("published") String dateStr) {
        return Book.of(isbn, name, LocalDate.parse(dateStr));
    }
}