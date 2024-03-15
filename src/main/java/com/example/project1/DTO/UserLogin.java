package com.example.project1.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {
    @NotBlank(message = "Username is not blank")
    private String username;
    @NotBlank(message = "Password is not blank")
    private String password;
}
