package org.trinogin.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.Game;

import java.util.List;

@Repository
public class GameRepository {
    private final JdbcTemplate jdbc;

    public GameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<Game> findAllGames() {
        return jdbc.query(
                "SELECT game_id, name FROM games ORDER BY name",
                (rs, rowNum) -> new Game(
                        rs.getString("game_id"),
                        rs.getString("name")
                )
        );
    }

    public void createGame(String name) {
        try {
            jdbc.update("INSERT INTO games (name) VALUES (?)", name);
        } catch (DuplicateKeyException e) {
        }
    }
}



