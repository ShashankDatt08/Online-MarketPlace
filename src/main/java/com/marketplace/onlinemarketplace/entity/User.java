package com.marketplace.onlinemarketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    @NotNull
    @NotBlank
    @Email(message = "Invalid Email Address")
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String Firstname;

    @NotNull
    @NotBlank
    private String Lastname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        CLIENT,
        FREELANCER
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    @ToString.Exclude
    private FreelancerProfile freelancerProfile;

    @JsonBackReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ClientProfile clientProfile;


}
