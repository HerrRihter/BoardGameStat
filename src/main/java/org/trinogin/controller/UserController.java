package org.trinogin.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.trinogin.dto.UserDTO;
import org.trinogin.dto.UserUpdateRequest;
import org.trinogin.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/board-game-stat")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public String getUser(Model model, Principal principal) {
        String username = principal.getName();
        UserDTO dto = userService.getUser(username);
        model.addAttribute("user", dto);
        return "user";
    }

    @GetMapping("/home")
    public String getUserHome(Model model, Principal principal) {
        String username = principal.getName();
        UserDTO dto = userService.getUser(username);
        model.addAttribute("user", dto);
        return "home";
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("newPassword") String newPassword, Principal principal) {
        String username = principal.getName();
        userService.changePassword(username, newPassword);
        return "redirect:/board-game-stat/me";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UserUpdateRequest userUpdateRequest, Principal principal) {
        userUpdateRequest.setUsername(principal.getName());

        userService.updateUser(userUpdateRequest);
        return "redirect:/board-game-stat/me";
    }
}