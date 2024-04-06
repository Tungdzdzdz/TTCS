package com.example.project1.service;

import com.example.project1.DTO.ClubStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.ClubStat;

import java.util.List;

public interface IClubStatService {
    void createClubStat(ClubStatDTO clubStatDTO) throws DataNotFoundException;
    List<ClubStat> getClubStatsByStartSeasonYear(int startSeasonYear) throws DataNotFoundException;
    List<ClubStat> getClubStatsBySeasonId(int seasonId) throws DataNotFoundException;
    ClubStat getClubStat(int clubId, int seasonId) throws DataNotFoundException;
}
