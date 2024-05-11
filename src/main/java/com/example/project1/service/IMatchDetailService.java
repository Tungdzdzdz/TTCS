package com.example.project1.service;

import java.util.List;

import com.example.project1.DTO.MatchDetailDTO;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Response.Statistics;

public interface IMatchDetailService {
    void createMatchDetail(Long matchDetail, MatchDetailDTO matchDetailDTO);
    List<String> getResultByWeek(int week, int seasonId);
    List<String> getLastResult(List<Long> matchesId);
    List<String> getResultMatchByClubStat(int clubStatId, int limit);
    String getResultByMatch(Long matchId);
    List<MatchDetail> getMatchDetailByMatch(Long matchId);
    Statistics getStatisticByMatch(Long matchId);
}
