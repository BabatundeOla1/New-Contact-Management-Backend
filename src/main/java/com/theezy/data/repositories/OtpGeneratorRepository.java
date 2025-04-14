package com.theezy.data.repositories;

import com.theezy.data.models.OtpGenerator;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpGeneratorRepository extends MongoRepository<OtpGenerator, String> {


    Optional<OtpGenerator> findByCode(String code);
    void deleteByExpirationTimeBefore(LocalDateTime expirationTime);

}
