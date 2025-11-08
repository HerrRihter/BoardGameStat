package org.trinogin.service;

import org.springframework.stereotype.Service;
import org.trinogin.entity.GameEntity;
import org.trinogin.dto.GameCreateRequest;
import org.trinogin.dto.GameUpdateRequest;
import org.trinogin.dto.GameDTO;
import org.trinogin.dto.GameDetailsDTO;
import org.trinogin.mapper.GameMapper;
import org.trinogin.repository.GameRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameDTO> getAllGameDTOs() {
        return gameRepository.findAllGames()
                .stream()
                .map(GameMapper::toGameDTO)
                .collect(Collectors.toList());
    }

    public List<GameDTO> getUserGameCollection(String username, UserService userService) {
        Set<Long> userGameIds = userService.getUserGameIds(username);
        // TODO userGameRepository.findUserGames, now it's too heavy
        return gameRepository.findAllGames().stream()
                .filter(game -> userGameIds.contains(game.getId()))
                .map(GameMapper::toGameDTO)
                .collect(Collectors.toList());
    }

    public GameDetailsDTO findGameDetailsByName(String name) {
        Optional<GameEntity> gameOpt = gameRepository.findByName(name);
        //TODO check how this error displayed on UI
        return gameOpt.map(GameMapper::toGameDetailsDTO).orElse(null);
    }

    public GameDetailsDTO findGameDetailsById(Long id) {
        Optional<GameEntity> gameOpt = gameRepository.findById(id);
        //TODO check how this error displayed on UI
        return gameOpt.map(GameMapper::toGameDetailsDTO).orElse(null);
    }

    public void addGame(GameCreateRequest request) {
        GameEntity game = GameMapper.fromGameCreateRequest(request);
        gameRepository.save(game);
    }

    public void updateGame(GameUpdateRequest request) {
        GameEntity game = GameMapper.fromGameUpdateRequest(request);
        gameRepository.update(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
