package org.trinogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameDetailsDTO {
    private Long id;
    private String name;
    private String gameType;
    private String desc;
    private int minPlayers;
    private int maxPlayers;
    private Timestamp createdAt;
}
