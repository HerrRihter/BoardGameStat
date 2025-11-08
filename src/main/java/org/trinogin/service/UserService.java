package org.trinogin.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.trinogin.entity.UserEntity;
import org.trinogin.dto.UserCreateRequest;
import org.trinogin.dto.UserDTO;
import org.trinogin.dto.UserUpdateRequest;
import org.trinogin.mapper.UserMapper;
import org.trinogin.repository.UserGamesRepository;
import org.trinogin.repository.UserRepository;

import java.util.*;

@Validated
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserGamesRepository userGamesRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, UserGamesRepository userGamesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userGamesRepository = userGamesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getUser(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(UserMapper::toUserDTO).orElse(null);
    }

    public void createUser(@Valid UserCreateRequest userCreateRequest) {
        boolean usernameExists = userRepository.existsByUsername(userCreateRequest.getUsername());
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

    public void updateUser(@Valid UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = UserMapper.fromUserUpdateRequest(userUpdateRequest);
        userRepository.update(userEntity);
    }

    public void changePassword(@NotBlank String username,@NotBlank String newPassword) {
        String hash = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(username, hash);
    }

    public Set<Long> getUserGameIds(String username) {
        Long userId = userRepository.findIdByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new HashSet<>(userGamesRepository.findGameIdsByUserId(userId));
    }

    public void addGamesToUser(String username, List<Long> gameIds) {
        Long userId = userRepository.findByUsername(username)
                .map(UserEntity::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        for (Long gameId : gameIds) {
            userGamesRepository.addGameToUser(userId, gameId);
        }
    }

    public void removeGamesFromUser(String username, List<Long> gameIds) {
        Long userId = userRepository.findByUsername(username)
                .map(UserEntity::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        for (Long gameId : gameIds) {
            userGamesRepository.removeGameFromUser(userId, gameId);
        }
    }

    public void addGameToUserById(String username, Long gameId) {
        Long userId = userRepository.findByUsername(username)
                .map(UserEntity::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userGamesRepository.addGameToUser(userId, gameId);
    }

    public void removeGameFromUserById(String username, Long gameId) {
        Long userId = userRepository.findByUsername(username)
                .map(UserEntity::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userGamesRepository.removeGameFromUser(userId, gameId);
    }

    public boolean hasGame(String username, Long gameId) {
        return getUserGameIds(username).contains(gameId);
    }
}