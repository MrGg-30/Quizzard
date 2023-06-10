package com.freeuni.quizzard.data.mongo.repository;

import com.freeuni.quizzard.data.mongo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}

