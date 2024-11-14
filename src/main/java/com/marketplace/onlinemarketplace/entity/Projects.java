package com.marketplace.onlinemarketplace.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Projects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String Title;

    @NotBlank
    private String ProjectDescription;

    private Long ClientId;

    private String ClientName;

    private String ClientEmail;

    @NotNull
    private Long budget;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public enum Status {
        OPEN,
        INPROGRESS,
        CLOSED,
    }
}
