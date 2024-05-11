package com.example.project1.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SeasonDTO {
    private Integer id;
    private String name;
    private String sponsor;
    private LocalDate startSeason;
    private LocalDate endSeason;
    private List<ClubPlayer> clubs;
    private Integer quanity;
}
