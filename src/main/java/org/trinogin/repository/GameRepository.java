package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.Game;
import org.trinogin.GameEntity;

import java.util.List;

@Repository
public class GameRepository {
    private final JdbcTemplate jdbc;

    public GameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<Game> findAllGames() {
        String query = "SELECT game_id, name FROM games ORDER BY name";
        return jdbc.query(
                query,
                (rs, rowNum) -> new Game(
                        rs.getLong("game_id"),
                        rs.getString("name")
                )
        );
    }

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



