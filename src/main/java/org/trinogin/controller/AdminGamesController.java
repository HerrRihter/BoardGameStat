package org.trinogin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.trinogin.dto.GameCreateRequest;
import org.trinogin.service.GameService;

@Controller
@RequestMapping("/board-game-stat/admin/games")
@PreAuthorize("hasAuthority('admin')")
public class AdminGamesController {

    private final GameService gameService;

    public AdminGamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String adminGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "admin-games";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute GameCreateRequest gameCreateRequest) {
        gameService.addGame(gameCreateRequest);
        return "redirect:/board-game-stat/admin/games";
    }
}

