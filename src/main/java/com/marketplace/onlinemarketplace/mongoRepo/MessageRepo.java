package com.marketplace.onlinemarketplace.mongoRepo;

import com.marketplace.onlinemarketplace.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.time.LocalDateTime;

public interface MessageRepo extends MongoRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findByReceiverIdAndSenderId(Long receiverId, Long senderId);
    List<Message> findByConversationIdOrderByTimestampAsc(String id);
    /**
     * Deletes all messages with a timestamp before the given date.
     *
     * @param timestamp the cutoff timestamp; messages older than this will be deleted.
     */
    void deleteByTimestampBefore(LocalDateTime timestamp);
}
