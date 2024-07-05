package com.example.project1.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.management.Notification;

import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.Notifycation;
import com.example.project1.Model.Squad;
import com.example.project1.Model.User;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.Response.MatchDetailResponse;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.service.ClubService;
import com.example.project1.service.ClubStatService;
import com.example.project1.service.CoachService;
import com.example.project1.service.MatchDetailService;
import com.example.project1.service.MatchService;
import com.example.project1.service.NotifycationService;
import com.example.project1.service.PlayerService;
import com.example.project1.service.SeasonService;
import com.example.project1.service.SquadService;
import com.example.project1.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
    private final NotifycationService notifycationService;
    private final ClubStatService clubStatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final EntityManager entityManager;

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
            @RequestBody UserDTO userDTO) throws Exception {

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
        Match match = matchService.getMatchById(matchDTO.getId());
        int seasonId = match.getSeason().getId();
        int matchWeek = match.getWeek();
        simpMessagingTemplate.convertAndSend(String.format("/topic/match/fixture/%d", match.getId()), match);
        simpMessagingTemplate.convertAndSend(String.format("/topic/fixture/season/%d/week/%d", seasonId, matchWeek), matchService.getMatchByWeek(matchWeek, seasonId));
        for(User x : match.getUsers())
        {
            simpMessagingTemplate.convertAndSend(String.format("/topic/notifycation/user/%d", x.getId()), notifycationService.createNotifycation(match, x, String.format("Update time for match: %s vs %s", match.getHomeClubStat().getClub().getName(), match.getAwayClubStat().getClub().getName()), "Update Time"));
        }
        return ResponseEntity.ok().body("Match time is updated successfully");
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
            @RequestBody List<SquadDTO> squadDTO) throws DataNotFoundException {
        squadService.updateSquads(squadDTO);
        return ResponseEntity.ok().body("Squad is updated successfully");
    }

    @PostMapping("/matchDetail/match/{matchId}")
    @Transactional
    public ResponseEntity<?> createMatchDetail(
            @PathVariable Long matchId,
            @RequestBody MatchDetailDTO matchDetailDTO) throws Exception {
        MatchDetail matchDetail = matchDetailService.createMatchDetail(matchId, matchDetailDTO);
        entityManager.flush();
        Match match = matchService.getMatchById(matchId);
        Integer seasonId = match.getSeason().getId();
        Integer currentWeek = match.getWeek();
        simpMessagingTemplate.convertAndSend(String.format("/topic/result/season/%d/week/%d", seasonId, currentWeek),
                matchDetailService.getResultByWeek(currentWeek, seasonId));
        simpMessagingTemplate.convertAndSend(String.format("/topic/match/%d", matchId),
                new MatchDetailResponse(matchDetail, MatchDetailResponse.Type.CREATE));
        simpMessagingTemplate.convertAndSend(String.format("/topic/match/result/%d", matchId),
                matchDetailService.getResultByMatch(matchId));
        List<ClubStat> clubStats = clubStatService.getTableBySeason(match.getSeason().getId())
                .stream()
                .map(cs -> {
                    entityManager.refresh(cs);
                    return cs;
                })
                .toList();
        simpMessagingTemplate.convertAndSend(String.format("/topic/clubstat/%d", match.getSeason().getId()), clubStats);
        for (User u : match.getUsers()) {
            Notifycation notifycation = notifycationService.createNotifycation(match, u, matchDetail);
            simpMessagingTemplate.convertAndSend(String.format("/topic/notifycation/user/%d", u.getId()), notifycation);
        }
        return ResponseEntity.ok().body("Match detail is created successfully");
    }

    @DeleteMapping("/matchDetail/{matchDetailId}")
    @Transactional
    public ResponseEntity<?> deleteMatchDetail(
            @PathVariable Long matchDetailId) throws Exception {
        MatchDetail matchDetail = matchDetailService.getMatchDetailById(matchDetailId);
        Long matchId = matchDetail.getMatch().getId();
        matchDetailService.deleteByMatchDetail(matchDetailId);
        entityManager.flush();
        Match match = matchService.getMatchById(matchId);
        Integer seasonId = match.getSeason().getId();
        Integer currentWeek = match.getWeek();
        simpMessagingTemplate.convertAndSend(String.format("/topic/result/season/%d/week/%d", seasonId, currentWeek),
                matchDetailService.getResultByWeek(currentWeek, seasonId));
        simpMessagingTemplate.convertAndSend(String.format("/topic/match/%d", match.getId()),
                new MatchDetailResponse(matchDetail, MatchDetailResponse.Type.DELETE));
        simpMessagingTemplate.convertAndSend(String.format("/topic/match/result/%d", match.getId()),
                matchDetailService.getResultByMatch(match.getId()));
        List<ClubStat> clubStats = clubStatService.getTableBySeason(match.getSeason().getId())
                .stream()
                .map(cs -> {
                    entityManager.refresh(cs);
                    return cs;
                })
                .toList();
        simpMessagingTemplate.convertAndSend(String.format("/topic/clubstat/%d", match.getSeason().getId()),clubStats);
        for (User u : match.getUsers()) {
            Notifycation notifycation = notifycationService.createNotifycation(match, u, matchDetail);
            simpMessagingTemplate.convertAndSend(String.format("/topic/notifycation/user/%d", u.getId()), notifycation);
        }
        return ResponseEntity.accepted().build();
    }
}