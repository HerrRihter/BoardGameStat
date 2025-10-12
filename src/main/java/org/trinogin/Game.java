package org.trinogin;

public class Game {
    private final Long gameId;
    private final String name;

    public Game(Long gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }
}



