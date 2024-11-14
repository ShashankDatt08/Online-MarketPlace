package com.marketplace.onlinemarketplace.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectRequest {

    private Long id;

    @NotBlank
    private String Title;

    @NotBlank
    private String ProjectDescription;

    @NotNull
    private Long budget;

    @NotNull
    private Projects.Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
