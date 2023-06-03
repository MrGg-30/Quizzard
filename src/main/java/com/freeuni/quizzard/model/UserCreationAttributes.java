package com.freeuni.quizzard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationAttributes {
    private String username;

    private String email;

    private String password;

    // TODO all other params later
}
