package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.service.MatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/match")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    @PostMapping("/create")
    public ResponseEntity<?> createMatch(
        @RequestBody MatchDTO matchDTO
    ) 
    {
        try {
            matchService.createMatch(matchDTO);
            return ResponseEntity.ok("Match created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
