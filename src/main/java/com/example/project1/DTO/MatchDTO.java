package com.example.project1.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class MatchDTO {
    private Long id;
    private Integer homeClubStatId;
    private Integer awayClubStatId;
    private int homeFormation;
    private int awayFormation; 
    private LocalDate date;
    private LocalTime time;
    private int seasonId;
    private int week;
}
