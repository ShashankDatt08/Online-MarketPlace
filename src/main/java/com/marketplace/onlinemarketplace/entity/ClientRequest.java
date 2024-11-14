package com.marketplace.onlinemarketplace.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequest {

    private Long id;

    @NotNull
    @NotBlank
    private String companyName;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String industry;

    @NotNull
    @NotBlank
    private String location;
}
