package com.chitterchallengespring.demo.services;

import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldBeAbleToSignUpAsNewUser() {

        UserService userService = new UserService(userRepository);

        User testUser = new User();

        when(userRepository.save(testUser)).thenReturn(testUser);

        User actual = userService.signup(testUser);

        Mockito.verify(userRepository).save(testUser);

        Assertions.assertEquals(testUser, actual);

    }

    @Test
    void shouldBeAbleToLoginIfRegistered() {

        UserService userService = new UserService(userRepository);

        User testUser = new User();
        testUser.setName("Test");
        testUser.setUsername("TestUsername");
        testUser.setEmail("Test@hotmail.com");
        testUser.setPassword("test");

        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        User actual = userService.login(testUser);

        Mockito.verify(userRepository).findByUsername(testUser.getUsername());

        Assertions.assertEquals(testUser, actual);


    }

    @Test
    void shouldGetErrorIfInvalidPassword() {

        UserService userService = new UserService(userRepository);

        User requestUser = new User();
        requestUser.setName("Test");
        requestUser.setUsername("TestUsername");
        requestUser.setEmail("Test@hotmail.com");
        requestUser.setPassword("abcdefg");

        User userInDatabase = new User();
        userInDatabase.setName("Test");
        userInDatabase.setUsername("TestUsername");
        userInDatabase.setEmail("Test@hotmail.com");
        userInDatabase.setPassword("test");

        when(userRepository.findByUsername("TestUsername")).thenReturn(Optional.of(userInDatabase));

        // because I want to check that an exception is throwm, here I use a special assertion with an executable.
        // this allows me to check the exception is thrown and stops program from terminating.
        Exception exception = Assertions.assertThrows(ResponseStatusException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.login(requestUser);
            }
        });

        Mockito.verify(userRepository).findByUsername(requestUser.getUsername());

        Assertions.assertEquals("400 BAD_REQUEST \"Incorrect password\"", exception.getMessage());

    }

    }