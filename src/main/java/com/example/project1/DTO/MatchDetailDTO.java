package com.example.project1.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class MatchDetailDTO {
    private int type;
    private int playerStat;
    private int clubStat;
    private int minute;
}
