package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByUserId1AndUserId2(Long userId1, Long userId2);
}