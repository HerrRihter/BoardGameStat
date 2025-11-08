package org.trinogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.trinogin.service.GameService;
import org.trinogin.service.UserService;
import java.security.Principal;
import java.util.*;
import org.trinogin.dto.GameDTO;
import org.trinogin.dto.GameDetailsDTO;

@Controller
@RequestMapping("/board-game-stat/games")
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("/collection")
    public String listUserGames(Model model, Principal principal) {
        List<GameDTO> userGames = gameService.getUserGameCollection(principal.getName(), userService);
        model.addAttribute("games", userGames);
        model.addAttribute("isCollection", true);
        return "games/collection";
    }

    @GetMapping("/library")
    public String listGameLib(Model model, Principal principal) {
        List<GameDTO> allGames = gameService.getAllGameDTOs();
        Set<Long> userGameIds = userService.getUserGameIds(principal.getName());
        model.addAttribute("games", allGames);
        model.addAttribute("userGameIds", userGameIds);
        model.addAttribute("isCollection", false);
        return "games/library";
    }

    @GetMapping("/{gameName}")
    public String gameDetail(@PathVariable("gameName") String gameName, Model model, Principal principal) {
        GameDetailsDTO game = gameService.findGameDetailsByName(gameName);
        boolean hasGame = userService.hasGame(principal.getName(), game.getId());
        model.addAttribute("game", game);
        model.addAttribute("hasGame", hasGame);
        return "games/game-detail";
    }

    @PostMapping("/add-to-user")
    public String addGamesToUser(@RequestParam(value = "gameIds", required = false) List<Long> gameIds, Principal principal) {
        userService.addGamesToUser(principal.getName(), gameIds);
        return "redirect:/board-game-stat/games/library";
    }

    @PostMapping("/remove-from-user")
    public String removeGamesFromUser(@RequestParam(value = "gameIds", required = false) List<Long> gameIds, Principal principal) {
        userService.removeGamesFromUser(principal.getName(), gameIds);
        return "redirect:/board-game-stat/games/collection";
    }

    @PostMapping("/add")
    public String addGame(@RequestParam("gameId") Long gameId, Principal principal) {
        userService.addGamesToUser(principal.getName(), List.of(gameId));
        return "redirect:/board-game-stat/games/library";
    }

    @PostMapping("/remove")
    public String removeGame(@RequestParam("gameId") Long gameId, Principal principal) {
        userService.removeGameFromUserById(principal.getName(), gameId);
        return "redirect:/board-game-stat/games/collection";
    }
}


