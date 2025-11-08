package org.trinogin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.trinogin.dto.GameCreateRequest;
import org.trinogin.dto.GameUpdateRequest;
import org.trinogin.service.GameService;
import org.trinogin.dto.GameDetailsDTO;

@Controller
@RequestMapping("/board-game-stat/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {

    private final GameService gameService;

    public AdminController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public String adminGames(Model model) {
        model.addAttribute("games", gameService.getAllGameDTOs());
        return "admin-games";
    }

    @GetMapping("/games/{gameId}")
    public String gameDetail(@PathVariable("gameId") Long gameId, Model model) {
        GameDetailsDTO gameDetails = gameService.findGameDetailsById(gameId);
        if (gameDetails != null) {
            model.addAttribute("game", gameDetails);
            return "admin/admin-game-detail";
        } else {
            //todo no even errors?
            return "redirect:/board-game-stat/admin/games";
        }
    }

    @PostMapping("/games/add")
    public String add(@ModelAttribute GameCreateRequest gameCreateRequest) {
        gameService.addGame(gameCreateRequest);
        return "redirect:/board-game-stat/admin/games";
    }

    @PostMapping("/games/{gameId}/update")
    public String update(@PathVariable("gameId") Long gameId, @ModelAttribute GameUpdateRequest gameUpdateRequest) {
        gameUpdateRequest.setId(gameId); // TODO <- manual setId? Why not in request initially
        gameService.updateGame(gameUpdateRequest);
        return "redirect:/board-game-stat/admin/games/" + gameId;
    }
//TODO DeleteMapping
    @PostMapping("/games/{gameId}/delete")
    public String delete(@PathVariable("gameId") Long gameId) {
        gameService.deleteGame(gameId);
        return "redirect:/board-game-stat/admin/games";
    }
}