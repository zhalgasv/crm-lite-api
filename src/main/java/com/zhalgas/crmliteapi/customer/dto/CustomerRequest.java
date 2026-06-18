package com.zhalgas.crmliteapi.customer.dto;

import com.zhalgas.crmliteapi.customer.CustomerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotBlank String name,
        @Email String email,
        String phone,
        String company,
        @NotNull CustomerStatus status,
        @NotNull Long ownerId
) {
}
