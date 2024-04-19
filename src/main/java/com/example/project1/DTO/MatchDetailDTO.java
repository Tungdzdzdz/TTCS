package com.example.project1.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class MatchDetailDTO {
    // private int matchId;
    // private int eventId;
    // private int playerStatId;
    // private int clubStatId;
    // private int eventTime;

    private int homeClubStatId;
    private int awayClubStatId;
    private long matchId;
}
