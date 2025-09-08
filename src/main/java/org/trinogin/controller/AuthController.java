package org.trinogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.trinogin.User;
import org.trinogin.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        //
        userService.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
}