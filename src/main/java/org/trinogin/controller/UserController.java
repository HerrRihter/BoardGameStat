package org.trinogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinogin.User;
import org.trinogin.service.UserService;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/all")
//    public List<Map<String, Object>> getUsers() {
//        String query = "SELECT * FROM usr";
//        return jdbcTemplate.queryForList(query);
//    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model) {
        logger.debug("Received getUser request with username:" + username);
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "user";
    }
}