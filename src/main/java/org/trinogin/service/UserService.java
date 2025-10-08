package org.trinogin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.trinogin.UserEntity;
import org.trinogin.dto.UserCreateRequest;
import org.trinogin.dto.UserDTO;
import org.trinogin.dto.UserUpdateRequest;
import org.trinogin.mapper.UserMapper;
import org.trinogin.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getUser(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(UserMapper::toUserDTO).orElse(null);
    }

    public void createUser(UserCreateRequest userCreateRequest) {
        boolean usernameExists = userRepository.existsByUsername(userCreateRequest.getUserName());
        boolean emailExists = userRepository.existsByEmail(userCreateRequest.getEmail());

        if (!usernameExists && !emailExists) {
            String passwordHash = passwordEncoder.encode(userCreateRequest.getPassword());
            UserEntity encryptedUser = UserMapper.fromUserCreateRequest(userCreateRequest);
            encryptedUser.setPassword(passwordHash);
            userRepository.save(encryptedUser);
        } else {

            throw new IllegalStateException(usernameExists ? "username" : "email" + " is not unique");
        }
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = UserMapper.fromUserUpdateRequest(userUpdateRequest);
        userRepository.update(userEntity);
    }

    public void changePassword(String username, String newPassword) {
        String hash = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(username, hash);
    }
}