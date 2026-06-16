package com.zhalgas.crmliteapi.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByUsername() {
        User user = User.builder()
                .username("adil")
                .password("password")
                .email("adil@test.com")
                .role(UserRole.MANAGER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("adil");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("adil@test.com");
    }
}
