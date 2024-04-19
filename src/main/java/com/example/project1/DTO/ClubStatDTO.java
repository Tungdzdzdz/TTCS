package com.example.project1.DTO;

import lombok.Builder;
import lombok.Data;

@Data
public class ClubStatDTO {
    private String clubName;
    private int seasonId;
    private int goalTaken;
    private int goalReceived;
    private int cleanSheet;
    private int yellowCard;
    private int redCard;
    private int win;
    private int draw;
    private int lose;
    private int point;
    private int rank;
    private int matchNumber;
    private int offside;
    private int saves;
    private int foul;
    private int shot;
}
