package com.theezy.data.repositories;

import com.theezy.data.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactRepository extends MongoRepository<Contact, String> {

    Optional<Contact> findContactByName(String name);
    Optional<Contact> findContactByPhoneNumber(String phoneNumber);
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
}
