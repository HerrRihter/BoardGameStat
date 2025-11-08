package org.trinogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameUpdateRequest {
    private Long id;
    private String name;
    private String gameType;
    private String desc;
    private int minPlayers;
    private int maxPlayers;
}

