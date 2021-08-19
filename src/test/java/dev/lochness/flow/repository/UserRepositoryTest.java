package dev.lochness.flow.repository;

import dev.lochness.flow.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DirtiesContext
    void shouldReturnUserByUsername() {
        User user = createUser();
        User actualUser = userRepository.findByUsername("user")
                .orElseThrow();
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    @DirtiesContext
    void shouldNotReturnUserByUsername() {
        User user = createUser();
        Optional<User> actualUser = userRepository.findByUsername("NO_SUCH_USER");
        Assertions.assertTrue(actualUser.isEmpty());
    }

    private User createUser() {
        return testEntityManager.persist(User.builder()
                .username("user")
                .email("email")
                .password("1111")
                .build());
    }
}