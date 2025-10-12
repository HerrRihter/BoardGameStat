package org.trinogin.repository;

import org.trinogin.UserEntity;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById(Long id);
    Optional<Long> findIdByUsername(String username);
    UserEntity save(UserEntity user);
    UserEntity update(UserEntity user);
    void updatePassword(String username, String passwordHash);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}