package org.trinogin.repository;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.trinogin.User;
import org.trinogin.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbc;
    private final UserRowMapper userRowMapper;
    private static final Logger logger = LoggerFactory.getLogger(JdbcUserRepository.class);


    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
        this.userRowMapper = new UserRowMapper();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {

        logger.debug("Finding user by username: {}", username);

        String query = """
                SELECT u.user_id, u.username, u.email, u.displayname, u.password_hash, r.role_name\s
                FROM users u JOIN roles r ON u.role = r.role_id WHERE u.username = ?""";

        try {
            UserEntity userEntity = jdbc.queryForObject(query, userRowMapper, username);
            logger.debug("User found:{}", userEntity);
            return Optional.of(userEntity);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            logger.debug("User not found by username {}", username);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        throw new UnsupportedOperationException("Method findById is not implemented");
    }

    @Override
    public UserEntity save(UserEntity user) {

        logger.debug("Creating new user:{}", user);
        String query = """
                INSERT INTO users (username, displayname, email, password_hash, role)
                VALUES (?, ?, ?, ?, (SELECT role_id FROM roles WHERE role_name = ?))
                RETURNING user_id""";

        try {
            Long newUserId = jdbc.queryForObject(
                    query,
                    Long.class,
                    user.getUserName(),
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAuthorities() == null ? "user" : user.getAuthorities()
            );

            logger.debug("User inserted:{}", newUserId);
            user.setUserId(newUserId);
            return user;
        } catch (Exception e) {
            logger.debug("Failed to insert user: {}; Error: {}", user.getUserName(), e.toString());
            throw new RuntimeException("Failed to insert user", e);
        }
    }

    @Override
    public UserEntity update(UserEntity user) {

        logger.debug("Updating user: {}", user);
        String query = "UPDATE users SET displayname = ?, email = ? WHERE username = ?";

        try {
            jdbc.update(query,
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getUserName());

            logger.debug("User updated:{}", user);

            return user;
        } catch (Exception e) {
            logger.debug("Failed to update user: {}; Error: {}", user.getUserName(), e.toString());
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    public void updatePassword(String username, String passwordHash) {

        logger.debug("Updating password for user:{}", username);
        String query = "UPDATE users SET password_hash = ? WHERE username = ?";

        try {
            jdbc.update(query, passwordHash, username);
            logger.debug("Password updated for user:{}", username);
        } catch (Exception e) {
            logger.debug("Failed to update password for user: {}; Error: {}", username, e.toString());
            throw new RuntimeException("Failed to update password for user", e);
        }
    }


    @Override
    public boolean existsByUsername(String username) {

        logger.debug("Checking if user exists by username={}", username);
        String query = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)";

        try {
            return jdbc.queryForObject(query, Boolean.class, username);
        } catch (Exception e) {
            logger.debug("Failed checking existing by username user: {}; Error: {}", username, e.toString());
            throw new RuntimeException("Failed checking existing by username user", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {

        logger.debug("Checking if user exists by email={}", email);
        String query = "SELECT EXISTS (SELECT 1 FROM users WHERE email = ?)";

        try {
            return jdbc.queryForObject(query, Boolean.class, email);
        } catch (Exception e) {
            logger.debug("Failed checking existing by email user: {}; Error: {}", email, e.toString());
            throw new RuntimeException("Failed checking existing by email user", e);
        }
    }

    private static class UserRowMapper implements RowMapper<UserEntity> {

        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserEntity(
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("displayname"),
                    rs.getLong("user_id"),
                    rs.getString("password_hash"),
                    rs.getString("role_name")
            );
        }
    }
}
