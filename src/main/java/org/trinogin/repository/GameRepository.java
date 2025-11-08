package org.trinogin.repository;

import org.trinogin.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    List<GameEntity> findAllGames();
    GameEntity save(GameEntity game);
    Optional<GameEntity> findByName(String name);
    Optional<GameEntity> findById(Long id);
    void deleteById(Long id);
    void update(GameEntity game);
}



