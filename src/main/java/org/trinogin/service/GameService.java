package org.trinogin.service;

import org.springframework.stereotype.Service;
import org.trinogin.Game;
import org.trinogin.repository.GameRepository;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAllGames();
    }

    public void addGame(String name) {
        gameRepository.createGame(name);
    }
}



