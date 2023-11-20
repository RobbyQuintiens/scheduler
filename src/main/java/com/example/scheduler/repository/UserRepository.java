package com.example.scheduler.repository;

import com.example.scheduler.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(String userId);
}
