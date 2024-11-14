package com.marketplace.onlinemarketplace.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password in Required")
    private String password;
}
