package com.marketplace.onlinemarketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "FreelancerProfiles")
@Data
public class FreelancerProfile {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "username", nullable = false)
    private String Username;

    private String portfolio;
    private String skills;
    private String bio;
    private double rating;
    private int completedProjectsCount;
}
