package com.example.project1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.DTO.CoachDTO;
import com.example.project1.DTO.MatchDTO;
import com.example.project1.DTO.MatchDetailDTO;
import com.example.project1.DTO.PlayerDTO;
import com.example.project1.DTO.SeasonDTO;
import com.example.project1.DTO.SquadDTO;
import com.example.project1.DTO.UserDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Squad;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.service.ClubService;
import com.example.project1.service.CoachService;
import com.example.project1.service.MatchDetailService;
import com.example.project1.service.MatchService;
import com.example.project1.service.PlayerService;
import com.example.project1.service.SeasonService;
import com.example.project1.service.SquadService;
import com.example.project1.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    private final UserService userService;
    private final PlayerService playerService;
    private final CoachService coachService;
    private final ClubService clubService;
    private final MatchService matchService;
    private final SeasonService seasonService;
    private final SquadService squadService;
    private final MatchDetailService matchDetailService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<?> getAllUser() {
        try {
            return ResponseEntity.ok().body(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(
            @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(userService.createUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(
            @RequestBody UserDTO userDTO) throws DataNotFoundException {

        userService.updateUser(userDTO);
        return ResponseEntity.ok().body("User updated successfully");

    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer userId) throws DataNotFoundException {

        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User updated successfully");

    }

    @PostMapping("/player")
    public ResponseEntity<?> createPlayer(
            @RequestBody PlayerDTO playerDTO) {
        try {
            playerService.createPlayer(playerDTO);
            return ResponseEntity.ok().body("Player created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PutMapping("/player")
    public ResponseEntity<?> updatePlayer(
            @RequestBody PlayerDTO playerDTO) {
        try {
            playerService.updatePlayer(playerDTO);
            return ResponseEntity.ok().body("Player created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @DeleteMapping("/player/{playerId}")
    public ResponseEntity<?> deletePlayer(
            @PathVariable Integer playerId) {
        try {
            playerService.deletePlayer(playerId);
            return ResponseEntity.ok().body("Player created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PostMapping("/coach")
    public ResponseEntity<?> createCoach(
            @RequestBody CoachDTO coachDTO) throws DataNotFoundException {
        try {
            coachService.createCoach(coachDTO);
            return ResponseEntity.ok().body("Coach is created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PutMapping("/coach")
    public ResponseEntity<?> updateCoach(
            @RequestBody CoachDTO coachDTO) {
        try {
            coachService.updateCoach(coachDTO);
            return ResponseEntity.ok().body("Coach is updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @DeleteMapping("/coach/{coachId}")
    public ResponseEntity<?> deleteCoach(
            @PathVariable Integer coachId) {
        try {
            coachService.deleteCoach(coachId);
            return ResponseEntity.ok().body("Coach is deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PostMapping("/club")
    public ResponseEntity<?> createClub(
            @RequestBody ClubDTO clubDTO) throws Exception {

        clubService.createClub(clubDTO);
        return ResponseEntity.ok().body("Club is created successfully");

    }

    @PutMapping("/club")
    public ResponseEntity<?> updateClub(
            @RequestBody ClubDTO clubDTO) {
        try {
            clubService.updateClub(clubDTO);
            return ResponseEntity.ok().body("Club is updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @DeleteMapping("/club/{clubId}")
    public ResponseEntity<?> deleteClub(
            @PathVariable Integer clubId) {
        try {
            clubService.deleteClub(clubId);
            return ResponseEntity.ok().body("Club is deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PutMapping("/match/time")
    public ResponseEntity<?> updateMatchTime(
            @RequestBody MatchDTO matchDTO) throws DataNotFoundException {
        // try {
            matchService.updateMatchTime(matchDTO);
            return ResponseEntity.ok().body("Match time is updated successfully");
        // } catch (Exception e) {
        //     return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        // }
    }

    @PostMapping("/season")
    public ResponseEntity<?> createSeason(
            @RequestBody SeasonDTO seasonDTO) throws Exception {
        try {
            seasonService.createSeason(seasonDTO);
            return ResponseEntity.ok().body("Season is created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PutMapping("/season")
    public ResponseEntity<?> updateSeason(
            @RequestBody SeasonDTO seasonDTO) throws Exception {
        seasonService.updateSeason(seasonDTO);
        return ResponseEntity.ok().body("Season is updated successfully");
    }

    @DeleteMapping("/season/{seasonId}")
    public ResponseEntity<?> deleteSeason(
            @PathVariable Integer seasonId) {
        try {
            seasonService.deleteSeason(seasonId);
            return ResponseEntity.ok().body("Season is deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @PutMapping("/squad")
    public ResponseEntity<?> updateSquad(
            @RequestBody List<SquadDTO> squadDTO
    ) throws DataNotFoundException 
    {
        squadService.updateSquads(squadDTO);
        return ResponseEntity.ok().body("Squad is updated successfully");
    }

    @PostMapping("/matchDetail/match/{matchId}")
    public ResponseEntity<?> createMatchDetail(
            @PathVariable Long matchId,
            @RequestBody MatchDetailDTO matchDetailDTO
    ) throws DataNotFoundException 
    {
        matchDetailService.createMatchDetail(matchId, matchDetailDTO);
        return ResponseEntity.ok().body("Match detail is created successfully");
    }
}