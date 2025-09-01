package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        try {
            asd
        } catch (IllegalAccessError e) {

        }

        return new User(
                userData.get("username").toString(),
                userData.get("email").toString(),
                userData.get("displayname").toString(),
                userData.get("userid").toString(),
                userData.get("password").toString()
        );
    }

    public tmp() {

    }
}

