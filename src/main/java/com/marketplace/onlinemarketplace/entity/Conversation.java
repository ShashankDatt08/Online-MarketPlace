package com.marketplace.onlinemarketplace.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Entity
@Document(collection = "conversations")
@CompoundIndex(name = "user_pair_idx", def = "{'userId1': 1, 'userId2': 1}", unique = true)
@Data
public class Conversation {

    @Id
    private String id;

    private Long userId1;

    private Long userId2;

    private LocalDateTime lastMessageTimestamp;

    private Long projectId;
}