package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.GameEntity;

import java.util.List;

@Repository
public class JdbcGameRepository implements GameRepository {
    private final JdbcTemplate jdbc;

    public JdbcGameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public List<GameEntity> findAllGames() {
        String query = "SELECT game_id, name, game_type, description, min_players, max_players, created_at FROM games ORDER BY name";
        return jdbc.query(
                query,
                (rs, rowNum) -> new GameEntity(
                        rs.getLong("game_id"),
                        rs.getString("name"),
                        rs.getString("game_type"),
                        rs.getString("description"),
                        rs.getInt("min_players"),
                        rs.getInt("max_players"),
                        rs.getTimestamp("created_at")
                )
        );
    }

    @Override
    public GameEntity save(GameEntity game) {
        String query = """
                INSERT INTO games (name, game_type, description, min_players, max_players)
                VALUES (?, ?, ?, ?, ?)
                RETURNING game_id""";

        Long newGameId = jdbc.queryForObject(
                query,
                Long.class,
                game.getName(),
                game.getGameType(),
                game.getDesc(),
                game.getMinPlayers(),
                game.getMaxPlayers()
        );

        game.setId(newGameId);
        return game;
    }
}


