package org.trinogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameCreateRequest {
    private String name;
    private String gameType;
    private String desc;
    private int minPlayers;
    private int maxPlayers;
}
