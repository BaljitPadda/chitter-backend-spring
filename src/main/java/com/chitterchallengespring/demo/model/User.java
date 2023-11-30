package com.chitterchallengespring.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users_")
public class User {

    @JsonProperty
    @NotEmpty(message = "A user must have a name.")
    private String name;

    @JsonProperty
    @NotEmpty(message = "A user must have a username.")
    @Indexed(unique=true)
    private String username;

    @JsonProperty
    @NotEmpty(message = "A user must have an email.")
    @Indexed(unique=true)
    private String email;

    @JsonProperty
    @NotEmpty(message = "A user must have a password.")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
