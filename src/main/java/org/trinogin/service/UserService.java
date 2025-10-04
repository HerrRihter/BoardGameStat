package org.trinogin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.trinogin.User;
import org.trinogin.UserEntity;
import org.trinogin.repository.JdbcUserRepository;
import org.trinogin.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(JdbcUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(String username) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByUsername(username);
            UserEntity extractedUserEntity = userEntity.get();
            return new User(
                    extractedUserEntity.getUserName(),
                    extractedUserEntity.getEmail(),
                    extractedUserEntity.getDisplayName(),
                    Long.toString(extractedUserEntity.getUserId()),
                    extractedUserEntity.getPassword(),
                    extractedUserEntity.getRole());
        } catch (Exception e) {
            return null;
        }
    }

    public void createUser(User user) {

        boolean usernameExists = userRepository.existsByUsername(user.getUsername());
        boolean emailExists = userRepository.existsByUsername(user.getEmail());

        if (!usernameExists && !emailExists) {
            String passwordHash = passwordEncoder.encode(user.getPassword());
            UserEntity encryptedUser = new UserEntity(user);
            encryptedUser.setPassword(passwordHash);
            userRepository.save(encryptedUser);
        } else {

            throw new IllegalStateException(usernameExists ? "username" : "email" + " is not unique");
        }
    }

    public void updateUser(User user) {
        UserEntity userEntity = new UserEntity(user);
        userRepository.update(userEntity);
    }

    public void changePassword(String username, String newPassword) {
        String hash = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(username, hash);
    }
}