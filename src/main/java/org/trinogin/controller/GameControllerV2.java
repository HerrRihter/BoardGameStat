package org.trinogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinogin.dto.GameDTO;
import org.trinogin.service.GameService;
import org.trinogin.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board-game-stat/api/v2/games")
public class GameControllerV2 {

    private final GameService gameService;
    private final UserService userService;

    public GameControllerV2(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("/collection")
    public Map<String, Object> listUserGames(Principal principal) {
        List<GameDTO> userGames = gameService.getUserGameCollection(principal.getName(), userService);
        return Map.of(
                "games", userGames,
                "isCollection", true
        );
    }
}
