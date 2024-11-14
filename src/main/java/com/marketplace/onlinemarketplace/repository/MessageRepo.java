package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findByReceiverIdAndSenderId(Long receiverId, Long senderId);
    List<Message> findBySenderIdOrReceiverIdOrderByTimestampAsc(Long senderId, Long receiverId);
}