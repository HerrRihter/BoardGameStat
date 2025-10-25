package org.trinogin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.trinogin.dto.GameCreateRequest;
import org.trinogin.service.GameService;

@Controller
@RequestMapping("/board-game-stat/admin/library")
@PreAuthorize("hasAuthority('admin')")
public class AdminGamesController {

    private final GameService gameService;

    public AdminGamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String adminGames(Model model) {
        //TODO implement gameService.getAllNames as first implementation
        model.addAttribute("games", gameService.getAllGames());
        //TODO later could be game.getAllGamesWithPagination()
        return "admin-games";
    }

    @PostMapping("/add") //TODO should be PutMapping??
    public String add(@ModelAttribute GameCreateRequest gameCreateRequest) {
        gameService.addGame(gameCreateRequest);
        return "redirect:/board-game-stat/admin/games";
    }

    //TODO add "/edit" (min/max players? description? and etc

    //TODO add "/delete"
}