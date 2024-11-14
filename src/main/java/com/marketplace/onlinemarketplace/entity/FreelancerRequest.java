package com.marketplace.onlinemarketplace.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FreelancerRequest {

    @NotNull
    @NotBlank
    private Long userId;

    private String portfolio;


    @NotNull
    @NotBlank
    private String skills;

    @NotNull
    @NotBlank
    private String bio;

    private double rating;

    private int completedProjectsCount;
}


