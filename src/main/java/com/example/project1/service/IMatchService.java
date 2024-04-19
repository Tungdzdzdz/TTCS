package com.example.project1.service;

import java.util.List;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Match;

public interface IMatchService {
    void createMatch(MatchDTO matchDTO) throws DataNotFoundException;
    void updateMatchWeek();
    List<Match> getMatchByWeek(int matchweek, int seasonId) throws DataNotFoundException;
    List<Match> getResultMatchByWeekAndSeason(int matchweek, int seasonId) throws DataNotFoundException;
    void autoPickSquadMatch(int clubStatId, long matchId) throws DataNotFoundException;
    List<Match> getNextMatchBySeason(int seasonId) throws DataNotFoundException;
    List<Match> getLastResultMatch(int seasonId) throws DataNotFoundException;
    List<Match> getNextMatchByClubStat(int clubStatId, int limit) throws DataNotFoundException;
    List<Match> getResultMatchByClubStat(int clubStatId, int limit) throws DataNotFoundException;
}
