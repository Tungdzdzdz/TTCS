package com.example.project1.controller;

import com.example.project1.DTO.PlayerDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.PlayerStat;
import com.example.project1.service.PlayerService;
import com.example.project1.service.PlayerStatService;

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
    private final PlayerStatService playerStatService;
    @GetMapping
    public ResponseEntity<?> getAllPlayers()
    {
        return ResponseEntity.ok().body(playerService.getAllPlayers());
    }

    @GetMapping("{playerId}/season")
    public ResponseEntity<?> getSeasonByPlayer(
            @PathVariable(name = "playerId") int playerId
    ) throws DataNotFoundException
    {
        return ResponseEntity.ok().body(playerStatService.getSeasonByPlayer(playerId));
    }
}
