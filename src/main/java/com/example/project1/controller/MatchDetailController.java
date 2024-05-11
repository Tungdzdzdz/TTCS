package com.example.project1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.DTO.MatchDetailDTO;
import com.example.project1.service.MatchDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matchdetail")
@CrossOrigin(origins = "http://localhost:5173")
public class MatchDetailController {
    private final MatchDetailService matchDetailService;

    @GetMapping("/result/{matchWeek}")
    public ResponseEntity<?> getAllResultByMatchWeek(
        @PathVariable(name = "matchWeek") int matchWeek,
        @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    ) 
    {
        try {
            return ResponseEntity.ok(matchDetailService.getResultByWeek(matchWeek, seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/result/last")
    public ResponseEntity<?> getAllResultByMatchWeek(
        @RequestBody List<Long> matchesId
    ) 
    {
        try {
            return ResponseEntity.ok(matchDetailService.getLastResult(matchesId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/result/club/{clubStatId}")
    public ResponseEntity<?> getAllResultByClubStat(
        @PathVariable(name = "clubStatId") int clubStatId,
        @RequestParam(name = "limit", required = false, defaultValue = "5") int limit
    ) 
    {
        try {
            return ResponseEntity.ok(matchDetailService.getResultMatchByClubStat(clubStatId, limit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/result/match/{matchId}")
    public ResponseEntity<?> getResultByMatch(
        @PathVariable(name = "matchId") Long matchId
    ) 
    {
        return ResponseEntity.ok(matchDetailService.getResultByMatch(matchId));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getMatchDetailByMatch(
        @PathVariable(name = "matchId") Long matchId
    ) 
    {
        return ResponseEntity.ok(matchDetailService.getMatchDetailByMatch(matchId));
    }

    @GetMapping("statistic/match/{matchId}")
    public ResponseEntity<?> getMatchStatistic(
        @PathVariable(name = "matchId") Long matchId
    ) 
    {
        return ResponseEntity.ok(matchDetailService.getStatisticByMatch(matchId));
    }
}
