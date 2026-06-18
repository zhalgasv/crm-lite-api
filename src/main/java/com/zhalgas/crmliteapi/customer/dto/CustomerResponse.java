package com.zhalgas.crmliteapi.customer.dto;
import com.zhalgas.crmliteapi.customer.CustomerStatus;


import java.time.LocalDateTime;


public record CustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        String company,
        CustomerStatus status,
        Long ownerId,
        String ownerUsername,
        LocalDateTime createdAt
) {
}
