package com.example.project1.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ClubPlayer {
    private ClubDTO club;
    private List<PlayerDTO> players;
    private List<PositionDTO> positions;
    private List<Integer> numberJersey;
    private CoachDTO coach;
}
