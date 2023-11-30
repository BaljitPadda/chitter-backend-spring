package com.chitterchallengespring.demo.controllers;

import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public User signUp(@Valid @RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping(value = "/login/{username}")
    @CrossOrigin
    public User login(@PathVariable String username, @Valid @RequestBody User user) {
        return userService.login(user);
    }
}