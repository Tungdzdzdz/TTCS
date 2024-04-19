package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Formation;
import com.example.project1.Model.Match;
import com.example.project1.Model.Player;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Season;
import com.example.project1.Model.Squad;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.FormationRepository;
import com.example.project1.repository.MatchDetailRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.PlayerStatRepository;
import com.example.project1.repository.SeasonRepository;
import com.example.project1.repository.SquadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService implements IMatchService{
    private final MatchRepository matchRepository;
    private final ClubStatRepository clubStatRepository;
    private final ClubRepository clubRepository;
    private final SeasonRepository seasonRepository;
    private final FormationRepository formationRepository;
    private final SquadRepository squadRepository;
    private final PlayerStatRepository playerStatRepository;
    private final MatchDetailRepository matchDetailRepository;
    @Override
    public void createMatch(MatchDTO matchDTO) throws DataNotFoundException {
        Match match = convertFromMatchDTO(matchDTO);
        matchRepository.save(match);
    }

    private Match convertFromMatchDTO(MatchDTO matchDTO) throws DataNotFoundException {
        Club homeClub = clubRepository
                        .findByName(matchDTO.getHomeClubId())
                        .orElseThrow(() -> new DataNotFoundException("Club is not found!"));
        Club awayClub = clubRepository
                        .findByName(matchDTO.getAwayClubId())
                        .orElseThrow(() -> new DataNotFoundException("Club is not found!"));
        Season season = seasonRepository
                        .findById(matchDTO.getSeasonId())
                        .orElseThrow(() -> new DataNotFoundException("Season is not found!"));
        ClubStat homeClubStat = clubStatRepository
                                .findByClubAndSeason(homeClub, season)
                                .orElseThrow(() -> new DataNotFoundException("Club stat is not found!"));
        ClubStat awayClubStat = clubStatRepository
                                .findByClubAndSeason(awayClub, season)
                                .orElseThrow(() -> new DataNotFoundException("Club stat is not found!"));
        Formation homeFormation = formationRepository
                                .findById(matchDTO.getHomeFormationId())
                                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        Formation awayFormation = formationRepository
                                .findById(matchDTO.getAwayFormationId())
                                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        Match match = new Match();
        match.setHomeClubStat(homeClubStat);
        match.setAwayClubStat(awayClubStat);
        match.setHomeFormation(homeFormation);
        match.setAwayFormation(awayFormation);
        match.setMatchDate(matchDTO.getMatchDate());
        return match;
    }

    @Override
    public void updateMatchWeek() {
        for(int i = 0; i < 38; i++)
        {
            for(int j = 1; j<=10; j++)
            {
                int matchId = i*10+j;
                if(matchId >= 300)
                {
                    matchId += 1;
                }
                Match match = matchRepository.findById((long) matchId).orElseThrow(null);
                match.setWeek(i+1);
                matchRepository.save(match);
            }
        }
    }

    @Override
    public List<Match> getMatchByWeek(int matchweek, int seasonId) throws DataNotFoundException {
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new DataNotFoundException("Season is not found!"));
        return matchRepository.findMatchByWeekAndSeasonOrderByMatchDate(matchweek, season);
    }

    @Override
    public void autoPickSquadMatch(int clubStatId, long matchId) throws DataNotFoundException {
        ClubStat clubStat = clubStatRepository.findById(clubStatId).orElseThrow(() -> new DataNotFoundException("Club Stat is not found!"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        PlayerStat goalKeeperPlayer = playerStatRepository.findRandomGoalkeeper(clubStat.getClub().getId());
        squadRepository.save(createSquad(goalKeeperPlayer, clubStat, match, true));
        List<PlayerStat> playerStats = playerStatRepository.findRandomOutfieldPlayer(goalKeeperPlayer.getPlayer().getId(), clubStat.getClub().getId());
        for(int i = 0; i < 10; i++)
        {
            squadRepository.save(createSquad(playerStats.get(i), clubStat, match, true));
        }
        for(int i = 10; i < 20; i++)
        {
            squadRepository.save(createSquad(playerStats.get(i), clubStat, match, false));
        }
    }
    
    private Squad createSquad(PlayerStat playerStat, ClubStat clubStat, Match match, boolean type) {
        Squad squad = new Squad();
        squad.setPlayerStat(playerStat);
        squad.setClubStat(clubStat);
        squad.setType(type);
        squad.setMatch(match);
        return squad;
    }

    @Override
    public List<Match> getResultMatchByWeekAndSeason(int matchweek, int seasonId) throws DataNotFoundException {
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new DataNotFoundException("Season is not found!"));
        List<Match> matches = matchRepository.findMatchByWeekAndSeasonOrderByMatchDate(matchweek, season);
        for(Match match : matches)
        {
            if(!matchDetailRepository.existsByMatch(match))
                matches.remove(match);
        }
        return matches;
    }

    @Override
    public List<Match> getNextMatchBySeason(int seasonId) throws DataNotFoundException {
        return matchRepository.findNextMatch(5, seasonId);
    }

    @Override
    public List<Match> getLastResultMatch(int seasonId) throws DataNotFoundException {
        return matchRepository.findLastResultMatch(5, seasonId);    
    }

    @Override
    public List<Match> getNextMatchByClubStat(int clubStatId, int limit) throws DataNotFoundException {
        return matchRepository.findNextMatchByClubStat(clubStatId, limit);
    }

    @Override
    public List<Match> getResultMatchByClubStat(int clubStatId, int limit) throws DataNotFoundException {
        return matchRepository.findLastResultMatchByClubStat(clubStatId, limit);
    }
}
