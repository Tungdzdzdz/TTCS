package com.example.project1.DTO;

import lombok.Data;

@Data
public class SquadDTO {
    private Long id;
    private Long matchId;
    private Integer playerStatId;
    private Integer clubStatId;
    private boolean type;
}
