package com.example.project1.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubDTO {
    private Integer id;
    @NotBlank(message = "Club's name is not blank")
    private String name;
    @NotBlank(message = "Club's short name is not blank")
    private String shortName;
    private String logo;
    @NotBlank(message = "Club's stadium is not blank")
    private String stadiumName;
    private String founded;
    private Integer location;
}
