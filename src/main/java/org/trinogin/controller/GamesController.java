package org.trinogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.trinogin.Game;
import org.trinogin.service.GameService;
import org.trinogin.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board-game-stat/games")
public class GamesController {

    private final GameService gameService;
    private final UserService userService;

    public GamesController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model, Principal principal) {
        List<Game> allGames = gameService.getAllGames();
        List<Long> userGameIds = userService.getUserGameIds(principal.getName());

        List<Game> myGames = new ArrayList<>();
        List<Game> otherGames = new ArrayList<>();
        for (Game g : allGames) {
            if (userGameIds.contains(g.getGameId())) {
                myGames.add(g);
            } else {
                otherGames.add(g);
            }
        }
        model.addAttribute("myGames", myGames);
        model.addAttribute("otherGames", otherGames);
        return "games";
    }

    @PostMapping("/add-to-user")
    public String addGameToUser(@RequestParam("gameId") Long gameId, Principal principal) {
        userService.addGameToUser(principal.getName(), gameId);

        return "redirect:/board-game-stat/games";
    }

    @PostMapping("/remove-from-user")
    public String removeGameFromUser(@RequestParam("gameId") Long gameId, Principal principal) {
        userService.removeGameFromUser(principal.getName(), gameId);

        return "redirect:/board-game-stat/games";
    }
}


