package org.example.user_service.repository;

import org.example.user_service.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindById_thenReturnUser() {
        User user = new User("Test User", "test@example.com", 25);
        entityManager.persistAndFlush(user);

        User found = userRepository.findById(user.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(user.getName());
    }

    @Test
    void whenFindByEmail_thenReturnUser() {
        User user = new User("Test User", "test@example.com", 25);
        entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByEmail(user.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    void whenFindByNonExistingEmail_thenReturnFalse() {
        boolean exists = userRepository.existsByEmail("nonexisting@example.com");

        assertThat(exists).isFalse();
    }
}