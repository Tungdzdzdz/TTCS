package com.example.project1.controller;

import com.example.project1.DTO.ClubStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.service.ClubStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clubstat")
@CrossOrigin(origins = "http://localhost:5173")
public class ClubStatController {
    private final ClubStatService clubStatService;
    @PostMapping("/create")
    public ResponseEntity<?> createClubStat(@RequestBody ClubStatDTO clubStatDTO) {
        try {
            clubStatService.createClubStat(clubStatDTO);
            return ResponseEntity.ok().body("Club stat created successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getClubStat(
            @PathVariable(name = "clubId") int clubId,
            @RequestParam(name = "seasonId") int seasonId
    ) {
        try {
            return ResponseEntity.ok().body(clubStatService.getClubStat(clubId, seasonId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<?> getClubStatsBySeason(
            @RequestParam(name = "seasonId") int seasonId
    ) {
        try {
            return ResponseEntity.ok().body(clubStatService.getClubStatsBySeasonId(seasonId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @GetMapping("/table/{seasonId}")
    public ResponseEntity<?> getTableBySeason(
        @PathVariable(name = "seasonId") int seasonId
    )
    {
        try {
            return ResponseEntity.ok().body(clubStatService.getTableBySeason(seasonId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}
