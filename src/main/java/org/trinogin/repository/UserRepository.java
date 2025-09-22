package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.User;


@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

public User getUserByUserName(String username) {
    try {
        return jdbc.queryForObject(
            "SELECT u.username, u.email, u.displayname, u.userid, u.password, r.role_name FROM users u JOIN roles r ON u.role_id = r.role_id WHERE username = ?",
            (rs, rowNum) -> new User(
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("displayname"),
                rs.getString("userid"),
                rs.getString("password"),
                rs.getString("role_name")
            ),
            username
        );
    } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
        return null;
    }
}
    public void createUser(User user) {
        jdbc.update("INSERT INTO users (username, displayname, email, password) VALUES (?, ?, ?, ?)",
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPassword());

        System.out.println(String.format("User inserted: %s, %s, %s",
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail()));
    }

    public void updateUser(User user) {
        jdbc.update("UPDATE users SET displayname = ?, email = ? WHERE username = ?",
                user.getDisplayName(),
                user.getEmail(),
                user.getUsername());

        System.out.println(String.format("User updated: %s, %s, %s",
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail()));
    }

    public void updateUserPassword(String username, String passwordHash) {
        jdbc.update("UPDATE users SET password = ? WHERE username = ?",
                passwordHash,
                username);
    }
}
