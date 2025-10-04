package org.trinogin.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.trinogin.User;
import org.trinogin.UserEntity;
import org.trinogin.repository.JdbcUserRepository;
import org.trinogin.repository.UserRepository;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JdbcUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(String username) {
        userRepository.findByUsername(username);
        return new User()
    }

    public void createUser(User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        User encryptedUser = new User(
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getUserId(),
                passwordHash,
                user.getAuthorities()
        );
        userRepository.createUser(encryptedUser);
    }

    public void saveUser(User user) {
        userRepository.updateUser(user);
    }

    public void changePassword(String username, String newPassword) {
        String hash = passwordEncoder.encode(newPassword);
        userRepository.updateUserPassword(username, hash);
    }
}