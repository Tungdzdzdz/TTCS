package com.example.project1.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MatchDTO {
    private String homeClubName;
    private String awayClubName;
    private int homeFormationId;
    private int awayFormationId; 
    private LocalDateTime matchDate;
    private int seasonId;
}
