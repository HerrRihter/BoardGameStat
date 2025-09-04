package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.User;

import java.util.Map;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public User getUserByUserName(String username) {
        String query = "SELECT * FROM usr WHERE username = '" + username + "'";
        Map<String, Object> userData = jdbc.queryForList(query).get(0);


        return new User(
                userData.get("username").toString(),
                userData.get("email").toString(),
                userData.get("displayname").toString(),
                userData.get("userid").toString(),
                userData.get("password").toString()
        );
    }

    public void saveUser(User user) {
        jdbc.update("INSERT INTO usr (username, displayname, email, password) VALUES (?, ?, ?, ?)",
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPassword());

        System.out.println(String.format("Kek user inserted: %s, %s, %s, %s",
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPassword()));
    }
}

