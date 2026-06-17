package com.zhalgas.crmliteapi.customer;

import com.zhalgas.crmliteapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByOwner(User owner);

    List<Customer> findByStatus(CustomerStatus status);

    List<Customer> findByOwnerAndStatus(User owner, CustomerStatus status);
}
