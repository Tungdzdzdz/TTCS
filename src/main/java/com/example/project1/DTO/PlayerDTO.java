package com.example.project1.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PlayerDTO {
    @NotBlank
    private String name;
    private LocalDate dateOfBirth;
    private boolean gender;
    private String countryName;
    @Min(value = 1, message = "Height must be greater than 0")
    private int height;
    @Min(value = 1, message = "Weight must be greater than 0")
    private int weight;
    private String img;
}
