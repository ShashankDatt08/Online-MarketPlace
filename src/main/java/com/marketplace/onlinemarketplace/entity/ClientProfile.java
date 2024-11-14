package com.marketplace.onlinemarketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "ClientProfiles")
@Data
public class ClientProfile {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private User user;

    @Column(name = "username", nullable = false)
    private String userName;

    @NotBlank
    @NotNull
    @Email(message = "Email is Required")
    private String email;

    private String companyName;
    private String industry;
    private String location;
}

