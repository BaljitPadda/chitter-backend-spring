package com.chitterchallengespring.demo.repositories;

import com.chitterchallengespring.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, String> {
}
