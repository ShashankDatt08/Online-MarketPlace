package com.marketplace.onlinemarketplace.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request payload for changing a user's password.
 */
@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}