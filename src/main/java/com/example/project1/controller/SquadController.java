package com.example.project1.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.service.SquadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/squad")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class SquadController {
    private final SquadService squadService;
    
    @GetMapping("/clubStat/{clubStatId}/match/{matchId}")
    public ResponseEntity<?> getSquadByClubStatAndMatch(
        @PathVariable Integer clubStatId,
        @PathVariable Long matchId
    ) throws DataNotFoundException
    {
        return ResponseEntity.ok(squadService.getSquadByClubStatAndMatch(clubStatId, matchId));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getSquadByMatchAndTypeAndInField(
        @PathVariable Long matchId
    ) throws DataNotFoundException
    {
        return ResponseEntity.ok(squadService.getSquadByMatch(matchId));
    }

    @GetMapping("/match/{matchId}/clubStat/{clubStatId}")
    public ResponseEntity<?> getSquadByMatchAndTypeAndInField(
        @PathVariable Long matchId,
        @PathVariable Integer clubStatId,
        @RequestParam String type,
        @RequestParam Boolean inField
    ) throws DataNotFoundException
    {
        if(type.equals("null"))
            return ResponseEntity.ok(squadService.getSquadInField(matchId, inField, clubStatId));
        else
            return ResponseEntity.ok(squadService.getSubSquad(matchId, Boolean.parseBoolean(type), inField, clubStatId));
    }
}
