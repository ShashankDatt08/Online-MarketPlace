package com.marketplace.onlinemarketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId1", "userId2"})
})
@Data
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId1;

    @Column(nullable = false)
    private Long userId2;

    @Column(nullable = false)
    private LocalDateTime lastMessageTimestamp;

}