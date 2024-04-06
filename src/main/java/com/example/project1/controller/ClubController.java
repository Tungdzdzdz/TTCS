package com.example.project1.controller;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.repository.CountryRepository;
import com.example.project1.service.ClubService;
import com.example.project1.service.PlayerStatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/club")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;
    private final PlayerStatService playerStatService;
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewClub(
            @Valid @RequestBody ClubDTO clubDTO
    ) {
        try {
            clubService.createClub(clubDTO);
            return ResponseEntity.ok("Create successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<?> getClub(
            @PathVariable(name = "clubId") int clubId
    ) {
        try {
            return ResponseEntity.ok(clubService.getClubById(clubId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/{clubId}/{seasonId}/playerstat")
    public ResponseEntity<?> getPlayStatOfClub(
            @PathVariable(name = "clubId") int clubId,
            @PathVariable(name = "seasonId") int seasonId
    ) {
        try {
            return ResponseEntity.ok(playerStatService.getAllPlayerStatsByClubIdAndSeasonId(clubId, seasonId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}
