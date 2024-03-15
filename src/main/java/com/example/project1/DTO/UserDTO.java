package com.example.project1.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Size(min = 8, message = "Username must be at least 8 character!")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 character!")
    private String password;

    @Size(min = 8, message = "Password must be at least 8 character!")
    private String retypePassword;

    @NotBlank(message = "Email is not blank")
    @Email(message = "The email is not correct format")
    private String email;

    private Integer roleId;
}
