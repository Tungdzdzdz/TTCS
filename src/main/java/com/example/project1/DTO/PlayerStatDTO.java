package com.example.project1.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerStatDTO {
    private String playerName;
    private String clubName;
    private int seasonId;
    private int goal;
    private int assist;
    private int cleanSheet;
    private int positionId;
    private int yellowCard;
    private int redCard;
    private int appearance;
    private int numberJersey;
}
