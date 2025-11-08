package org.trinogin.mapper;

import org.trinogin.entity.GameEntity;
import org.trinogin.dto.GameCreateRequest;
import org.trinogin.dto.GameUpdateRequest;
import org.trinogin.dto.GameDTO;
import org.trinogin.dto.GameDetailsDTO;

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

    public static GameEntity fromGameUpdateRequest(GameUpdateRequest gameUpdateRequest) {
        return new GameEntity(
                gameUpdateRequest.getId(),
                gameUpdateRequest.getName(),
                gameUpdateRequest.getGameType(),
                gameUpdateRequest.getDesc(),
                gameUpdateRequest.getMinPlayers(),
                gameUpdateRequest.getMaxPlayers(),
                null
        );
    }

    public static GameDTO toGameDTO(GameEntity entity) {
        if (entity == null) return null;
        return new GameDTO(
            entity.getId(),
            entity.getName(),
            entity.getGameType(),
            entity.getMinPlayers(),
            entity.getMaxPlayers()
        );
    }

    public static GameDetailsDTO toGameDetailsDTO(GameEntity entity) {
        if (entity == null) return null;
        return new GameDetailsDTO(
            entity.getId(),
            entity.getName(),
            entity.getGameType(),
            entity.getDesc(),
            entity.getMinPlayers(),
            entity.getMaxPlayers(),
            entity.getCreatedAt()
        );
    }

    public static GameEntity fromGameDTO(GameDTO dto) {
        if (dto == null) return null;
        return new GameEntity(
            dto.getId(),
            dto.getName(),
            dto.getGameType(),
            null,
            dto.getMinPlayers(),
            dto.getMaxPlayers(),
            null
        );
    }

    public static GameEntity fromGameDetailsDTO(GameDetailsDTO detailsDTO) {
        if (detailsDTO == null) return null;
        return new GameEntity(
            detailsDTO.getId(),
            detailsDTO.getName(),
            detailsDTO.getGameType(),
            detailsDTO.getDesc(),
            detailsDTO.getMinPlayers(),
            detailsDTO.getMaxPlayers(),
            detailsDTO.getCreatedAt()
        );
    }
}
