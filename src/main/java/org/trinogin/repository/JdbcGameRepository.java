package org.trinogin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.trinogin.entity.GameEntity;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<GameEntity> findByName(String name) {
        String query = "SELECT game_id, name, game_type, description, min_players, max_players, created_at FROM games WHERE name = ?";
        try {
            GameEntity game = jdbc.queryForObject(
                    query,
                    (rs, rowNum) -> new GameEntity(
                            rs.getLong("game_id"),
                            rs.getString("name"),
                            rs.getString("game_type"),
                            rs.getString("description"),
                            rs.getInt("min_players"),
                            rs.getInt("max_players"),
                            rs.getTimestamp("created_at")
                    ),
                    name
            );
            return Optional.ofNullable(game);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GameEntity> findById(Long id) {
        String query = "SELECT game_id, name, game_type, description, min_players, max_players, created_at FROM games WHERE game_id = ?";
        try {
            GameEntity game = jdbc.queryForObject(
                    query,
                    (rs, rowNum) -> new GameEntity(
                            rs.getLong("game_id"),
                            rs.getString("name"),
                            rs.getString("game_type"),
                            rs.getString("description"),
                            rs.getInt("min_players"),
                            rs.getInt("max_players"),
                            rs.getTimestamp("created_at")
                    ),
                    id
            );
            return Optional.ofNullable(game);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM games WHERE game_id = ?";
        jdbc.update(query, id);
    }

    @Override
    public void update(GameEntity game) {
        String query = """
                UPDATE games
                SET name = ?, game_type = ?, description = ?, min_players = ?, max_players = ?
                WHERE game_id = ?""";
        jdbc.update(query,
                game.getName(),
                game.getGameType(),
                game.getDesc(),
                game.getMinPlayers(),
                game.getMaxPlayers(),
                game.getId()
        );
    }
}


