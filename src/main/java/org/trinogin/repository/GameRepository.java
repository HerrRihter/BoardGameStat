package org.trinogin.repository;

import org.trinogin.GameEntity;

import java.util.List;

public interface GameRepository {
    List<GameEntity> findAllGames();
    GameEntity save(GameEntity game);
}



