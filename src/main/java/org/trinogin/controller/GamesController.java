package org.trinogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.trinogin.GameEntity;
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

    // list all games
    // select ALL and displays <- no filtering whether games is already added

    @GetMapping("collection")
    public String listUserGames(Model model, Principal principal) {
        List<String> userGames = userService.getUserGameNames(principal.getName());
        model.addAttribute("myGames", userGames);
        return "games";
    }

/*    @GetMapping("library")
    public String listGameLib(Model model, Principal p) {
        List<String> allGames = gameService.getAllGames();

        model.addAttribute(allGames);
        return "game-lib";
    }*/

    @GetMapping("/{gameName}") //<- add html page, it should include 2 buttons (add/remove)



    @PostMapping("/add-to-user") //TODO Long gameId ->  String gameName -> List<String> gameNames (to be added)
    public String addGameToUser(@RequestParam("gameId") Long gameId, Principal principal) {

        userService.addGameToUser(principal.getName(), gameId);

        return "redirect:/board-game-stat/games";
    }

    @PostMapping("/remove-from-user") //TODO Long gameId ->  String gameName -> List<String> gameNames (to be deleted)
    public String removeGameFromUser(@RequestParam("gameId") Long gameId, Principal principal) {
        userService.removeGameFromUser(principal.getName(), gameId);

        return "redirect:/board-game-stat/games";
    }
}


