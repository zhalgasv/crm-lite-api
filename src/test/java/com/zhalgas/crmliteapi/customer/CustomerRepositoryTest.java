package com.zhalgas.crmliteapi.customer;

import com.zhalgas.crmliteapi.customer.Customer;
import com.zhalgas.crmliteapi.customer.CustomerRepository;
import com.zhalgas.crmliteapi.customer.CustomerStatus;
import com.zhalgas.crmliteapi.user.User;
import com.zhalgas.crmliteapi.user.UserRepository;
import com.zhalgas.crmliteapi.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindCustomerByOwner() {
        User owner = User.builder()
                .username("adil")
                .password("password")
                .email("manager@test.com")
                .role(UserRole.MANAGER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(owner);

        Customer customer = Customer.builder()
                .name("Test Customer")
                .email("customer@test.com")
                .phone("87001234567")
                .company("Test company")
                .status(CustomerStatus.NEW)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .build();

        customerRepository.save(customer);

        List<Customer> result = customerRepository.findByOwner(owner);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Customer");
    }

    @Test
    void shouldFindByStatus() {
        User owner = User.builder()
                .username("adil")
                .password("password")
                .email("manager@test.com")
                .role(UserRole.MANAGER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(owner);

        Customer customer = Customer.builder()
                .name("Test Customer")
                .email("customer@test.com")
                .phone("87001234567")
                .company("Test company")
                .status(CustomerStatus.NEW)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .build();

        customerRepository.save(customer);

        List<Customer> result = customerRepository.findByStatus(CustomerStatus.NEW);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Customer");
    }

    @Test
    void shouldFindByOwnerAndStatus() {
        User owner = User.builder()
                .username("adil")
                .password("password")
                .email("manager@test.com")
                .role(UserRole.MANAGER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(owner);

        Customer customer = Customer.builder()
                .name("Test Customer")
                .email("customer@test.com")
                .phone("87001234567")
                .company("Test company")
                .status(CustomerStatus.NEW)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .build();

        customerRepository.save(customer);

        List<Customer> result = customerRepository.findByOwnerAndStatus(owner, CustomerStatus.NEW);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOwner().getUsername()).isEqualTo("adil");
    }
}
