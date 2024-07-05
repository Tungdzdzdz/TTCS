package com.example.project1.Response;

import com.example.project1.Model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private String token;
    private String role;
}
