package org.trinogin.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinogin.User;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/user")
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getUsers() {
        String query = "SELECT * FROM usr";
        return jdbcTemplate.queryForList(query);
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id, Model model) {
        String query = "SELECT * FROM usr WHERE userid = " + id;
        Map<String, Object> userData = jdbcTemplate.queryForList(query).get(0);

        User user = new User(
                userData.get("username").toString(),
                userData.get("email").toString(),
                userData.get("displayname").toString(),
                userData.get("userid").toString()
        );
        model.addAttribute("user", user);
        return "user";
    }
}