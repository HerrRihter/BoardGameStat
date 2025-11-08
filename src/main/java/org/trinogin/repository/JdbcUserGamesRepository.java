package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JdbcUserGamesRepository implements UserGamesRepository {
    private final JdbcTemplate jdbc;
    public JdbcUserGamesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Long> findGameIdsByUserId(Long userId) {
        String sql = "SELECT game_id FROM users_games WHERE user_id = ?";
        return jdbc.query(sql, (rs, rowNum) -> rs.getLong("game_id"), userId);
    }

    @Override
    public List<String> findGameNamesByUserId(Long userId) {
        String sql = "SELECT game_id FROM users_games WHERE user_id = ?";
        return jdbc.query(sql, (rs, rowNum) -> rs.getString("name"), userId);
    }

    @Override
    public void addGameToUser(Long userId, Long gameId) {
        String sql = "INSERT INTO users_games (user_id, game_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbc.update(sql, userId, gameId);
    }

    @Override
    public void removeGameFromUser(Long userId, Long gameId) {
        String sql = "DELETE FROM users_games WHERE user_id = ? AND game_id = ?";
        jdbc.update(sql, userId, gameId);
    }
}



