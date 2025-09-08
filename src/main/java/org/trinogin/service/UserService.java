package org.trinogin.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.trinogin.User;
import org.trinogin.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(String username) {
        return userRepository.getUserByUserName(username);
    }

    public void saveUser(User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        User ecriptedUser = new User(user.getUsername(), user.getEmail(), user.getDisplayName(), user.getUserId(), passwordHash);
        userRepository.saveUser(ecriptedUser);

        // TODO validator + SQL operation status check
    }
}