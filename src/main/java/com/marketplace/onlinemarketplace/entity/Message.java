package com.marketplace.onlinemarketplace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Entity
@Document(collection = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long receiverId;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status {
        SENT,
        DELIVERED,
        READ
    }

    private String conversationId;


}
