package com.example.project1.service;

import org.springframework.stereotype.Service;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Formation;
import com.example.project1.Model.Match;
import com.example.project1.Model.Season;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.FormationRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService implements IMatchService{
    private final MatchRepository matchRepository;
    private final ClubStatRepository clubStatRepository;
    private final ClubRepository clubRepository;
    private final SeasonRepository seasonRepository;
    private final FormationRepository formationRepository;
    @Override
    public void createMatch(MatchDTO matchDTO) throws DataNotFoundException {
        Match match = convertFromMatchDTO(matchDTO);
        matchRepository.save(match);
    }

    private Match convertFromMatchDTO(MatchDTO matchDTO) throws DataNotFoundException {
        Club homeClub = clubRepository
                        .findByName(matchDTO.getHomeClubName())
                        .orElseThrow(() -> new DataNotFoundException("Club is not found!"));
        Club awayClub = clubRepository
                        .findByName(matchDTO.getAwayClubName())
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
    
}
