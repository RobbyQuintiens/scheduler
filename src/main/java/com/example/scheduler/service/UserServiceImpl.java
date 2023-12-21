package com.example.scheduler.service;

import com.example.scheduler.dto.CustomerDto;
import com.example.scheduler.exception.UserNotFoundException;
import com.example.scheduler.model.User;
import com.example.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findByUserId(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
