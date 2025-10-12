package org.trinogin.mapper;

import org.trinogin.GameEntity;
import org.trinogin.dto.GameCreateRequest;

import java.sql.Timestamp;

public class GameMapper {
    public static GameEntity fromGameCreateRequest(GameCreateRequest gameCreateRequest) {
        return new GameEntity(
                null,
                gameCreateRequest.getName(),
                gameCreateRequest.getGameType(),
                gameCreateRequest.getDesc(),
                gameCreateRequest.getMinPlayers(),
                gameCreateRequest.getMaxPlayers(),
                null
        );
    }
}
