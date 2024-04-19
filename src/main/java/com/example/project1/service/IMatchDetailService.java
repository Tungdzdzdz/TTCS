package com.example.project1.service;

import java.util.List;

import com.example.project1.DTO.MatchDetailDTO;

public interface IMatchDetailService {
    void createMatchDetail(MatchDetailDTO matchDetailDTO);
    List<String> getResultByWeek(int week, int seasonId);
    List<String> getLastResult(List<Long> matchesId);
    List<String> getResultMatchByClubStat(int clubStatId, int limit);
}
