package com.example.project1.service;

import com.example.project1.DTO.PlayerStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.Player;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Position;
import com.example.project1.Model.Season;

import java.util.List;

public interface IPlayerStatService {
    void createPlayerStats(List<PlayerStatDTO> playerStatDTOList) throws DataNotFoundException;
    void createPlayerStat(Player player, Club club, Season season, Position position, Integer number) throws DataNotFoundException;
    void updatePlayerStat(PlayerStat playerStat) throws DataNotFoundException;
    void deletePlayerStat(Integer playerStatId) throws DataNotFoundException;
    List<PlayerStat> getAllPlayerStats(int seasonId) throws DataNotFoundException;
    List<PlayerStat> getAllPlayerStatsByClubIdAndSeasonId(int clubId, int seasonId) throws DataNotFoundException;
    PlayerStat getPlayerStat(int playerId, int seasonId) throws DataNotFoundException;
    PlayerStat getRandomPlayerStatBySeason(int seasonId) throws DataNotFoundException;
    List<Integer> getStatMatch(int playerStatId) throws DataNotFoundException; 
    List<Season> getSeasonByPlayer(int playerId) throws DataNotFoundException;
}
