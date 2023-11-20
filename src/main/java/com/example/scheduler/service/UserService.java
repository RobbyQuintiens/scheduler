package com.example.scheduler.service;

import com.example.scheduler.model.User;

import java.util.Optional;

public interface UserService {

    User findUserById(String id);
    User findById(int id);
}
