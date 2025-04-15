package com.theezy.data.repositories;

import com.theezy.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserById(String id);
//    Optional<User> findByEmail(String email);
    boolean existsUserById(String id);
//    User findUserByContact_Email(String email);
    Optional<User> findUserByContact_Email(String email);
    boolean existsUserByContact_PhoneNumber(String phoneNumber);
    boolean existsUserByContact_Email(String email);
}
