package com.chitterchallengespring.demo.services;

import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.PeepRepository;
import com.chitterchallengespring.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> signup(User user) {
        return Collections.singletonList(userRepository.save(user));
    }

    public User login(User user) {
        List<User> list = userRepository.findAll();

        for (User u : list) {
            if (u.getUsername().equals(user.getUsername())) {
                return user;
            }
        } throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
    }
}
//        if (loggedIn) {
//             System.out.println("Welcome " + user.getName());
//        } else {
//             System.out.println("Sorry can't log you in.");
//        }
//        return user;
//    }
//}
