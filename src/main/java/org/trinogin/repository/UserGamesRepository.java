package org.trinogin.repository;

import java.util.List;

public interface UserGamesRepository {
    List<Long> findGameIdsByUserId(Long userId);
    List<String> findGameNamesByUserId(Long userId);
    void addGameToUser(Long userId, Long gameId);
    void removeGameFromUser(Long userId, Long gameId);
}