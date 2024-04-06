package com.example.project1.service;

import com.example.project1.DTO.PlayerStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.PlayerStat;

import java.util.List;

public interface IPlayerStatService {
    void createPlayerStats(List<PlayerStatDTO> playerStatDTOList) throws DataNotFoundException;
    List<PlayerStat> getAllPlayerStats(int seasonId) throws DataNotFoundException;
    List<PlayerStat> getAllPlayerStatsByClubIdAndSeasonId(int clubId, int seasonId) throws DataNotFoundException;
    PlayerStat getPlayerStat(int playerId, int seasonId) throws DataNotFoundException;
}
