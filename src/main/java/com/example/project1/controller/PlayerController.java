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
    @PostMapping("/create")
    public ResponseEntity<?> createPlayer(
            @RequestBody List<PlayerDTO> playerDTOList
            ) {
        try {
            playerService.createPlayers(playerDTOList);
            return ResponseEntity.ok("Player created");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
