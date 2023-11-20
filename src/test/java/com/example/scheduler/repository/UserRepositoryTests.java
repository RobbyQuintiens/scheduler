package com.example.scheduler.repository;

import com.example.scheduler.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Test
    public void findByIdTest() {
        createUser(1, "user");
        Optional<User> foundUser = userRepository.findById(user.getId());

        assertThat(foundUser.get().getUsername()).isEqualTo("user");
    }

    @Test
    public void findByUsernameTest() {
        createUser(1, "user");
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        assertThat(foundUser.get().getUsername()).isEqualTo("user");
    }

    @Test
    public void findByUserIdTest() {
        createUser(1, "user");
        Optional<User> foundUser = userRepository.findByUserId(user.getUserId());

        assertThat(foundUser.get().getUsername()).isEqualTo("user");
    }

    private void createUser(int id, String username) {
        user = new User();
        user.setId(id);
        user.setUserId("12");
        user.setUsername(username);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        userRepository.save(user);
    }
}
