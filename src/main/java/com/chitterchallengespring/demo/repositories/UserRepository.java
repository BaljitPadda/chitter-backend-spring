package com.chitterchallengespring.demo.repositories;

import com.chitterchallengespring.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    // Spring data will automatically generate the query for this.
    // There will either be a user of that username or not.
    // An email will either already be in use or not.
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
