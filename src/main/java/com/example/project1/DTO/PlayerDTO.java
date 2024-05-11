package com.example.project1.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDTO {
    private Integer id;
    @NotBlank
    private String name;
    private String dateOfBirth;
    private boolean gender;
    private Integer country;
    @Min(value = 1, message = "Height must be greater than 0")
    private Integer height;
    @Min(value = 1, message = "Weight must be greater than 0")
    private Integer weight;
    private String img;
}
