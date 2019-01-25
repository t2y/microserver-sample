package com.example.microserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class AuthUser {

    @Getter
    private final String name;

    public boolean hasRole(String role) {
        if (this.name.equals("admin")) {
            return true;
        }
        // confirm role for user
        return false;
    }
}
