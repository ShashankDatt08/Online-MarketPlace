package com.marketplace.onlinemarketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private Long freelancerId;

    private Long amount;

    private String proposal;

    private LocalDateTime bidDate;

    private String FreelancerName;

    @Enumerated(EnumType.STRING)
    private BidStatus status;

    public enum BidStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}

