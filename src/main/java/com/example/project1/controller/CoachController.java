package com.example.project1.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Response.ErrorResponse;
import com.example.project1.service.ClubCoachService;
import com.example.project1.service.CoachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/coach")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CoachController {
    private final ClubCoachService clubCoachService;
    @GetMapping()
    public ResponseEntity<?> getAllCoach(
        @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    ) {
        try {
            return ResponseEntity.ok().body(clubCoachService.getClubCoachBySeason(seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomCoach(
        @RequestParam(name = "seasonId", required = false, defaultValue = "1") int seasonId
    )
    {
        try {
            return ResponseEntity.ok().body(clubCoachService.getClubCoachRandom(seasonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}
