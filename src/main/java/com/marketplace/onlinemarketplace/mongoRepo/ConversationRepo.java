package com.marketplace.onlinemarketplace.mongoRepo;

import com.marketplace.onlinemarketplace.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConversationRepo extends MongoRepository<Conversation, Long> {
    Optional<Conversation> findByUserId1AndUserId2(Long userId1, Long userId2);
    Optional<Conversation> findByUserId1AndUserId2AndProjectId(Long userId1, Long userId2, Long projectId);

}