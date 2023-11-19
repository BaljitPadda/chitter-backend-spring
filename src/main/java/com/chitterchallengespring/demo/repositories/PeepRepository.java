package com.chitterchallengespring.demo.repositories;

import com.chitterchallengespring.demo.model.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeepRepository extends MongoRepository <Peep, String> {


}
