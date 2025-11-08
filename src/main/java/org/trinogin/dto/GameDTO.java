package org.trinogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private Long id;
    private String name;
    private String gameType;
    private int minPlayers;
    private int maxPlayers;
}
