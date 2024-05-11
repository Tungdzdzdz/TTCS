package com.example.project1.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.DTO.MatchDetailDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.service.MatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/match")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    @GetMapping
    public ResponseEntity<?> getMatchByWeek(
        @RequestParam("matchweek") int matchweek,
        @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    ) {
        try {
            return ResponseEntity.ok(matchService.getMatchByWeek(matchweek, seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/next")
    public ResponseEntity<?> getNextMatch(
        @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId, 
        @RequestParam(name = "limit", required = false, defaultValue = "5") int limit
    ) {
        try {
            return ResponseEntity.ok(matchService.getNextMatchBySeason(seasonId, limit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // @PostMapping("/create/squad/auto")
    // public ResponseEntity<?> createMatchDetailSquad(
    //     @RequestBody MatchDetailDTO matchDetailDTO
    // ) 
    // {
    //     try {
    //         matchService.autoPickSquadMatch(matchDetailDTO.getHomeClubStatId(), matchDetailDTO.getMatchId());
    //         matchService.autoPickSquadMatch(matchDetailDTO.getAwayClubStatId(), matchDetailDTO.getMatchId());
    //         return ResponseEntity.ok("Match detail created successfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @GetMapping("/result/last")
    public ResponseEntity<?> getLastResultMatch(
        @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    )
    {
        try {
            return ResponseEntity.ok(matchService.getLastResultMatch(seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/fixture/club/{clubStatId}")
    public ResponseEntity<?> getFixtureByClubStat(
        @PathVariable(name = "clubStatId") Integer clubStatId
    ){
        try {
            return ResponseEntity.ok(matchService.getNextMatchByClubStat(clubStatId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/result/club/{clubStatId}")
    public ResponseEntity<?> getResultByClubStat(
        @PathVariable(name = "clubStatId") Integer clubStatId,
        @RequestParam(name = "limit", required = false, defaultValue = "5") Integer limit
    ){
        try {
            return ResponseEntity.ok(matchService.getResultMatchByClubStat(clubStatId, limit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}
