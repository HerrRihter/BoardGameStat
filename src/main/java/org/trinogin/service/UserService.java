package org.trinogin.service;

import org.springframework.stereotype.Service;
import org.trinogin.User;
import org.trinogin.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String username) {
        return userRepository.getUserByUserName(username);
    }
}
