package com.marketplace.onlinemarketplace.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BidRequest {

    @NotNull
    private Long projectId;

    @NotNull
    private Long freelancerId;

    @NotNull
    private BigDecimal amount;

    private String proposal;
}

