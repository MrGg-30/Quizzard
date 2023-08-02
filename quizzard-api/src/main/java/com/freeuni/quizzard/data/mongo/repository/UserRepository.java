package com.freeuni.quizzard.data.mongo.repository;

import com.freeuni.quizzard.data.mongo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUsername(String username);

    @Query("{ 'username': { $regex: '^?0', $options: 'i' } }")
    List<User> findUsersByUsernamePrefix(String prefix);
}

