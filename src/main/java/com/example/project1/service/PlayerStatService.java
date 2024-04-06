package com.example.project1.service;

import com.example.project1.DTO.PlayerStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.*;
import com.example.project1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerStatService implements IPlayerStatService{
    private final PlayerStatRepository playerStatRepository;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final SeasonRepository seasonRepository;
    private final PositionRepository positionRepository;
    @Override
    public void createPlayerStats(List<PlayerStatDTO> playerStatDTOList) throws DataNotFoundException {
        for(PlayerStatDTO x : playerStatDTOList)
        {
            playerStatRepository.save(toPlayerStat(x));
        }
    }

    @Override
    public List<PlayerStat> getAllPlayerStats(int seasonId) throws DataNotFoundException {
        Season season = seasonRepository
                .findById(seasonId)
                .orElseThrow(() -> new DataNotFoundException("Season not found"));
        return playerStatRepository.findPlayerStatBySeason(season);
    }

    @Override
    public List<PlayerStat> getAllPlayerStatsByClubIdAndSeasonId(int clubId, int seasonId) throws DataNotFoundException {
        Club club = clubRepository
                .findById(clubId)
                .orElseThrow(() -> new DataNotFoundException("Club not found"));
        Season season = seasonRepository
                .findById(seasonId)
                .orElseThrow(() -> new DataNotFoundException("Season not found"));
        return playerStatRepository.findPlayerStatByClubAndSeason(club, season);
    }

    public PlayerStat getPlayerStat(int playerId, int seasonId) throws DataNotFoundException {
        Player player = playerRepository
                .findById(playerId)
                .orElseThrow(() -> new DataNotFoundException("Player not found"));
        Season season = seasonRepository
                .findById(seasonId)
                .orElseThrow(() -> new DataNotFoundException("Season not found"));
        return playerStatRepository.findByPlayerAndSeason(player, season)
                .orElseThrow(() -> new DataNotFoundException("Player stat not found"));
    }

    private PlayerStat toPlayerStat(PlayerStatDTO playerStatDTO) throws DataNotFoundException {
        Player player = playerRepository
                .findByName(playerStatDTO.getPlayerName())
                .orElseThrow(() -> new DataNotFoundException("Player not found"));
        Club club = clubRepository
                .findByName(playerStatDTO.getClubName())
                .orElseThrow(() -> new DataNotFoundException("Club not found"));
        Season season = seasonRepository
                .findById(playerStatDTO.getSeasonId())
                .orElseThrow(() -> new DataNotFoundException("Season not found"));
        Position position = positionRepository
                .findById(playerStatDTO.getPositionId())
                .orElseThrow(() -> new DataNotFoundException("Position not found"));
        return PlayerStat
                .builder()
                .player(player)
                .club(club)
                .season(season)
                .appearance(playerStatDTO.getAppearance())
                .goal(playerStatDTO.getGoal())
                .assist(playerStatDTO.getAssist())
                .numberJersey(playerStatDTO.getNumberJersey())
                .redCard(playerStatDTO.getRedCard())
                .yellowCard(playerStatDTO.getYellowCard())
                .cleanSheet(playerStatDTO.getCleanSheet())
                .position(position)
                .build();
    }
}
