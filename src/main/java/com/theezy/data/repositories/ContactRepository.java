package com.theezy.data.repositories;

import com.theezy.data.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactRepository extends MongoRepository<Contact, String> {
    Optional<Contact> findByNameAndUserId(String userId, String name);
    Optional<Contact> findByName(String name);
    Optional<Contact> findByPhoneNumberAndUserId(String phoneNumber, String userId);
    Optional<Contact> findContactByPhoneNumber(String phoneNumber);
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Contact> findContactById(String contactId);
    void deleteContactByName(String name);
    void deleteContactByPhoneNumber(String phoneNumber);
}