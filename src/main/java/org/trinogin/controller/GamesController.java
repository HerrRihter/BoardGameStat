package org.trinogin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.trinogin.service.GameService;

@Controller
@RequestMapping("/board-game-stat/games")
public class GamesController {

    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String list(Model model, Authentication authentication) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("isAdmin", isAdmin(authentication));
        return "games";
    }

    @PostMapping("/add")
    public String add(@RequestParam("name") String name) {
        gameService.addGame(name.trim());
        return "redirect:/board-game-stat/games";
    }

    private boolean isAdmin(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if ("admin".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}


