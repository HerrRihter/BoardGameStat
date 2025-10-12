package org.trinogin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
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
    public Optional<Long> findIdByUsername(String username) {
        String query = "SELECT user_id FROM users WHERE username = ?";
        Long id = jdbc.queryForObject(query, Long.class, username);
        return Optional.of(id);
    }

    @Override
    public UserEntity save(UserEntity user) {

        logger.debug("Creating new user:{}", user);
        String query = """
                INSERT INTO users (username, displayname, email, password_hash, role)
                VALUES (?, ?, ?, ?, (SELECT role_id FROM roles WHERE role_name = ?))
                RETURNING user_id""";

        Long newUserId = jdbc.queryForObject(
                query,
                Long.class,
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities() == null ? "user" : user.getAuthorities()
        );

        logger.debug("User inserted:{}", newUserId);
        user.setUserId(newUserId);
        return user;
    }

    @Override
    public UserEntity update(UserEntity user) {

        logger.debug("Updating user: {}", user);
        String query = "UPDATE users SET displayname = ?, email = ? WHERE username = ?";

        jdbc.update(query,
                user.getDisplayName(),
                user.getEmail(),
                user.getUsername());

        logger.debug("User updated:{}", user);

        return user;
    }

    @Override
    public void updatePassword(String username, String passwordHash) {

        logger.debug("Updating password for user:{}", username);
        String query = "UPDATE users SET password_hash = ? WHERE username = ?";

        jdbc.update(query, passwordHash, username);
        logger.debug("Password updated for user:{}", username);
    }


    @Override
    public boolean existsByUsername(String username) {

        logger.debug("Checking if user exists by username={}", username);
        String query = "SELECT COUNT(1) FROM users WHERE username = ?";

        Integer count = jdbc.queryForObject(query, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {

        logger.debug("Checking if user exists by email={}", email);
        String query = "SELECT COUNT(1) FROM users WHERE email = ?";

        Integer count = jdbc.queryForObject(query, Integer.class, email);
        return count != null && count > 0;
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
