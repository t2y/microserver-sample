package com.example.microserver.model;

import java.time.LocalDate;

import com.example.microserver.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    public final int id;
    public final String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    public final LocalDate birthDay;

    @JsonCreator
    public static User create(
            @JsonProperty("name") String name,
            @JsonProperty("birthDay") String dateStr) {
        return User.of(-1, name, LocalDate.parse(dateStr));
    }
}