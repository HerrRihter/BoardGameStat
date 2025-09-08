package org.trinogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.trinogin.User;
import org.trinogin.service.UserService;

import java.security.Principal;
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


    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model) {
        logger.debug("Received getUser request with username:" + username);
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/home")
    public String getUserHome(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "home";
    }
}