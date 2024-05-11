package com.example.project1.controller;

import com.example.project1.DTO.PlayerDTO;
import com.example.project1.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    @GetMapping
    public ResponseEntity<?> getAllPlayers()
    {
        return ResponseEntity.ok().body(playerService.getAllPlayers());
    }
}
