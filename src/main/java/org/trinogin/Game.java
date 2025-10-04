package org.trinogin;

public class Game {
    private final String gameId;
    private final String name;

    public Game(String gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public String getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }
}



