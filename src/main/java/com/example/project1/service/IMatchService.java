package com.example.project1.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Formation;
import com.example.project1.Model.Match;
import com.example.project1.Model.Season;

public interface IMatchService {
    void createMatch(MatchDTO matchDTO) throws DataNotFoundException;
    List<Match> getMatchByWeek(int matchweek, int seasonId) throws DataNotFoundException;
    void autoPickSquadMatch(int clubStatId, long matchId) throws DataNotFoundException;
    List<Match> getNextMatchBySeason(int seasonId, int limit) throws DataNotFoundException;
    List<Match> getLastResultMatch(int seasonId) throws DataNotFoundException;
    List<Match> getNextMatchByClubStat(int clubStatId) throws DataNotFoundException;
    List<Match> getResultMatchByClubStat(int clubStatId, int limit) throws DataNotFoundException;
    void updateMatchTime(MatchDTO matchDTO) throws DataNotFoundException;
    void createFixtures(List<ClubStat> clubStats, Season season) throws DataNotFoundException;
    void createFixture(ClubStat home, ClubStat away, Formation homeFormation, Formation awayFormation, Season season, LocalDateTime matchDate, Integer week) throws DataNotFoundException;
    void deleteFixtures(List<ClubStat> clubStats, Season season) throws DataNotFoundException;
    void createFollower(Long matchId, String username);
    void deleteFollower(Long matchId, String username);
}
