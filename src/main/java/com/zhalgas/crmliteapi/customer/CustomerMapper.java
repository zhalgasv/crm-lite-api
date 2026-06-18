package com.zhalgas.crmliteapi.customer;

import com.zhalgas.crmliteapi.customer.dto.CustomerRequest;
import com.zhalgas.crmliteapi.customer.dto.CustomerResponse;
import com.zhalgas.crmliteapi.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCompany(),
                customer.getStatus(),
                customer.getOwner().getId(),
                customer.getOwner().getUsername(),
                customer.getCreatedAt()
        );
    }


    public Customer toEntity(CustomerRequest request, User owner) {
        return Customer.builder()
                        .name(request.name())
                        .email(request.email())
                        .phone(request.phone())
                        .company(request.company())
                        .status(request.status())
                        .owner(owner)
                        .createdAt(LocalDateTime.now())
                        .build();
    }
}
