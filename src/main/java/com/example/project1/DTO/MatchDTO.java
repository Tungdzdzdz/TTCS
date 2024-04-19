package com.example.project1.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MatchDTO {
    private String homeClubId;
    private String awayClubId;
    private int homeFormationId;
    private int awayFormationId; 
    private LocalDateTime matchDate;
    private int seasonId;
    private int week;
}
