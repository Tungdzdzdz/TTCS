package com.example.project1.controller;

import com.example.project1.DTO.PlayerStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.service.PlayerStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/playerstat")
@CrossOrigin(origins = "http://localhost:5173")
public class PlayerStatController {
    private final PlayerStatService playerStatService;
//    @PostMapping("/create")
//    public ResponseEntity<?> createPlayerStats(
//            @RequestBody List<PlayerStatDTO> playerStatDTOList
//    )
//    {
//       try{
//           playerStatService.createPlayerStats(playerStatDTOList);
//           return ResponseEntity.ok().body("Player stats created successfully");
//       } catch (Exception e) {
//           return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
//       }
//    }

    @GetMapping
    public ResponseEntity<?> getAllPlayerStats(
            @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    ) {
        try {
            return ResponseEntity.ok().body(playerStatService.getAllPlayerStats(seasonId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerStat(
            @PathVariable(name = "playerId") int playerId,
            @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    )
    {
        try {
            return ResponseEntity.ok().body(playerStatService.getPlayerStat(playerId, seasonId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}