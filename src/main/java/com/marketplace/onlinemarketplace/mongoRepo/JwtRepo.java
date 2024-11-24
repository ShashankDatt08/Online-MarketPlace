package com.marketplace.onlinemarketplace.mongoRepo;

import com.marketplace.onlinemarketplace.Jwt.JwtToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JwtRepo extends MongoRepository<JwtToken , String> {
    Optional<JwtToken> findByToken(String token);
}
