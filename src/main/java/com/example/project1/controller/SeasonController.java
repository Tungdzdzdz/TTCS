package com.example.project1.controller;

import com.example.project1.Response.ErrorResponse;
import com.example.project1.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/season")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SeasonController {
    private final SeasonService seasonService;
    @GetMapping("")
    public ResponseEntity<?> getAllSeasons() {
        return ResponseEntity.ok().body(seasonService.getAllSeasons());
    }
    
    @GetMapping("/currentweek/{seasonId}")
    public ResponseEntity<?> getCurrentWeek(
        @PathVariable Integer seasonId
    ) {
        try {
            return ResponseEntity.ok().body(seasonService.getCurrentWeek(seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/{seasonId}")
    public ResponseEntity<?> getSeasonById(
        @PathVariable Integer seasonId
    ) {
        try {
            return ResponseEntity.ok().body(seasonService.getSeasonById(seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}
