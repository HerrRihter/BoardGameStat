package org.trinogin;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GameEntity {
    private Long id;
    private String name;
    private String gameType;
    private String desc;
    private int minPlayers;
    private int maxPlayers;
    private Timestamp createdAt;
}