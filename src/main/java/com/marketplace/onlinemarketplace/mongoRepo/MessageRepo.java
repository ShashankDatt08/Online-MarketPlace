package com.marketplace.onlinemarketplace.mongoRepo;

import com.marketplace.onlinemarketplace.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepo extends MongoRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findByReceiverIdAndSenderId(Long receiverId, Long senderId);
    List<Message> findByConversationIdOrderByTimestampAsc(String id);

    /**
     * Deletes all messages with a timestamp before the specified date.
     *
     * @param timestamp the cutoff date; messages older than this will be deleted
     * @return the number of messages deleted
     */
    long deleteByTimestampBefore(LocalDateTime timestamp);
}
