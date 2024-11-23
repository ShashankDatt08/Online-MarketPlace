package com.marketplace.onlinemarketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID
    private Long id;

    @Column(nullable = false)
    private Long reviewerId;

    private String reviewerName;

    @Column(nullable = false)
    private String freelancerName;

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private int rating;

    private String comment;

    private LocalDateTime timestamp;
}
