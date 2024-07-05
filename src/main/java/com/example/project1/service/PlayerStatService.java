package com.example.project1.service;

import com.example.project1.DTO.PlayerStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.*;
import com.example.project1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerStatService implements IPlayerStatService {
    private final PlayerStatRepository playerStatRepository;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final SeasonRepository seasonRepository;
    private final PositionRepository positionRepository;
    private final SquadRepository squadRepository;
    private final MatchRepository matchRepository;
    private final MatchDetailRepository matchDetailRepository;
    private final EventRepository eventRepository;

    @Override
    public void createPlayerStats(List<PlayerStatDTO> playerStatDTOList) throws DataNotFoundException {
        for (PlayerStatDTO x : playerStatDTOList) {
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
    public List<PlayerStat> getAllPlayerStatsByClubIdAndSeasonId(int clubId, int seasonId)
            throws DataNotFoundException {
        if(!clubRepository.existsById(clubId) || !seasonRepository.existsById(seasonId))
                throw new DataNotFoundException("Club or Season not found");
        return playerStatRepository.findByClubAndSeason(clubId, seasonId);
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
                .saves(playerStatDTO.getSaves())
                .shot(playerStatDTO.getShot())
                .offside(playerStatDTO.getOffside())
                .foul(playerStatDTO.getFoul())
                .build();
    }

    @Override
    public List<Integer> getStatMatch(int playerStatId) throws DataNotFoundException {
        List<Long> matches = squadRepository.findAppearanceMatchByPlayerStat(playerStatId, true);
        Event goalEvent = eventRepository.findById(1).orElseThrow(() -> new DataNotFoundException("Event not found"));
        Event fullTimEvent = eventRepository.findByName("Full-Time");
        List<Integer> stats = new ArrayList<>(Arrays.asList(0, 0, 0));
        for(Long x : matches) {
            Match match = matchRepository.findById(x).orElseThrow(() -> new DataNotFoundException("Match not found"));
            if(matchDetailRepository.countByMatchAndEventAndClubStat(match, fullTimEvent, null) == 0)
                continue;
            int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, match.getHomeClubStat());
            int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, match.getAwayClubStat());
            if(homeGoal > awayGoal)
                stats.set(0, stats.get(0) + 1);
            else if(homeGoal < awayGoal)
                stats.set(1, stats.get(1) + 1);
            else
                stats.set(2, stats.get(2) + 1);
        }
        return stats;
    }

    @Override
    public PlayerStat getRandomPlayerStatBySeason(int seasonId) throws DataNotFoundException {
        return playerStatRepository.findRandomPlayerStatBySeasonId(seasonId);
    }

    @Override
    public void createPlayerStat(Player player, Club club, Season season, Position position, Integer number) throws DataNotFoundException {
        PlayerStat playerStat = new PlayerStat();
        playerStat.setPlayer(player);
        playerStat.setClub(club);
        playerStat.setSeason(season);
        playerStat.setPosition(position);
        playerStat.setNumberJersey(number);
        playerStat.setDefault();
        playerStatRepository.save(playerStat);
    }

    @Override
    public void updatePlayerStat(PlayerStat playerStat) throws DataNotFoundException {
        if (playerStatRepository.existsById(playerStat.getId())) {
            playerStatRepository.save(playerStat);
        }
    }

    @Override
    public void deletePlayerStat(Integer playerStatId) throws DataNotFoundException {
        playerStatRepository.deleteById(playerStatId);
    }

    @Override
    public List<Season> getSeasonByPlayer(int playerId) throws DataNotFoundException {
        Player player = playerRepository
                .findById(playerId)
                .orElseThrow(() -> new DataNotFoundException("Player not found"));
        List<PlayerStat> playerStats = playerStatRepository.findByPlayer(player);
        return playerStats.stream().map(PlayerStat::getSeason).toList();
    }       
}
