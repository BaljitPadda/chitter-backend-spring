package com.chitterchallengespring.demo.services;

import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That username is already in use.");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That email is already registered.");
        }
        return userRepository.save(user);
    }

    public User login(User user) {

        Optional<User> registeredUser = userRepository.findByUsername(user.getUsername());

        if (registeredUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
        }
        if(!registeredUser.get().getPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        }
        return user;
    }
}

