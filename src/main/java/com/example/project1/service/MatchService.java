package com.example.project1.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.example.project1.Model.User;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.FormationRepository;
import com.example.project1.repository.MatchDetailRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.PlayerStatRepository;
import com.example.project1.repository.SeasonRepository;
import com.example.project1.repository.SquadRepository;
import com.example.project1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService implements IMatchService {
    private final MatchRepository matchRepository;
    private final ClubStatRepository clubStatRepository;
    private final ClubRepository clubRepository;
    private final SeasonRepository seasonRepository;
    private final FormationRepository formationRepository;
    private final SquadRepository squadRepository;
    private final PlayerStatRepository playerStatRepository;
    private final MatchDetailRepository matchDetailRepository;
    private final UserRepository userRepository;

    @Override
    public void createMatch(MatchDTO matchDTO) throws DataNotFoundException {
        Match match = convertFromMatchDTO(matchDTO);
        matchRepository.save(match);
    }

    private Match convertFromMatchDTO(MatchDTO matchDTO) throws DataNotFoundException {
        Club homeClub = clubRepository
                .findById(matchDTO.getHomeClubStatId())
                .orElseThrow(() -> new DataNotFoundException("Club is not found!"));
        Club awayClub = clubRepository
                .findById(matchDTO.getAwayClubStatId())
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
                .findById(matchDTO.getHomeFormation())
                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        Formation awayFormation = formationRepository
                .findById(matchDTO.getAwayFormation())
                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        Match match = new Match();
        match.setHomeClubStat(homeClubStat);
        match.setAwayClubStat(awayClubStat);
        match.setHomeFormation(homeFormation);
        match.setAwayFormation(awayFormation);
        LocalDateTime matchDateTime = LocalDateTime.of(matchDTO.getDate(), matchDTO.getTime());
        match.setMatchDate(matchDateTime);
        match.setSeason(season);
        return match;
    }

    @Override
    public List<Match> getMatchByWeek(int matchweek, int seasonId) throws DataNotFoundException {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new DataNotFoundException("Season is not found!"));
        return matchRepository.findMatchByWeekAndSeasonOrderByMatchDate(matchweek, season);
    }

    @Override
    public void autoPickSquadMatch(int clubStatId, long matchId) throws DataNotFoundException {
        ClubStat clubStat = clubStatRepository.findById(clubStatId)
                .orElseThrow(() -> new DataNotFoundException("Club Stat is not found!"));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        PlayerStat goalKeeperPlayer = playerStatRepository.findRandomGoalkeeper(clubStat.getClub().getId());
        squadRepository.save(createSquad(goalKeeperPlayer, clubStat, match, true));
        List<PlayerStat> playerStats = playerStatRepository
                .findRandomOutfieldPlayer(goalKeeperPlayer.getPlayer().getId(), clubStat.getClub().getId());
        for (int i = 0; i < 10; i++) {
            squadRepository.save(createSquad(playerStats.get(i), clubStat, match, true));
        }
        for (int i = 10; i < 20; i++) {
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
    public List<Match> getNextMatchBySeason(int seasonId, int limit) throws DataNotFoundException {
        List<Match> matches = matchRepository.findNextMatch(limit, seasonId);
        return matches;
    }

    @Override
    public List<Match> getLastResultMatch(int seasonId) throws DataNotFoundException {
        return matchRepository.findLastResultMatch(5, seasonId);
    }

    @Override
    public List<Match> getNextMatchByClubStat(int clubStatId) throws DataNotFoundException {
        List<Match> matches = matchRepository.findNextMatchByClubStat(clubStatId);
        for(Match x : matches)
            System.out.println(x.getHomeClubStat().getId() + " " + x.getAwayClubStat().getId());
        return matches;
    }

    @Override
    public List<Match> getResultMatchByClubStat(int clubStatId, int limit) throws DataNotFoundException {
        return matchRepository.findLastResultMatchByClubStat(clubStatId, limit);
    }

    @Override
    public void updateMatchTime(MatchDTO matchDTO) throws DataNotFoundException {
        Match match = matchRepository.findById(matchDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        LocalDateTime matchDateTime = LocalDateTime.of(matchDTO.getDate(), matchDTO.getTime());
        System.out.println(matchDTO.getAwayFormation());
        System.out.println(matchDTO.getHomeFormation());
        Formation homeFormation = formationRepository.findById(matchDTO.getHomeFormation())
                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        Formation awayFormation = formationRepository.findById(matchDTO.getAwayFormation())
                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        match.setMatchDate(matchDateTime);
        match.setHomeFormation(homeFormation);
        match.setAwayFormation(awayFormation);
        matchRepository.save(match);
    }

    @Override
    public void createFixtures(List<ClubStat> clubStats, Season season) throws DataNotFoundException {
        LocalDate date = season.getStartSeason();
        Formation formation = formationRepository.findById(1)
                .orElseThrow(() -> new DataNotFoundException("Formation is not found!"));
        Random random = new Random();
        LocalDate[] dates = {
                date,
                date.plusDays(1),
                date.plusDays(2)
        };
        LocalTime[] localTimes = {
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
        };
        List<LocalDateTime> matchDates = new ArrayList<>();
        for (LocalDate d : dates) {
            for (LocalTime t : localTimes) {
                matchDates.add(LocalDateTime.of(d, t));
            }
        }
        for (int i = 0; i < clubStats.size() - 1; i++) {
            for (int j = 0; j < clubStats.size()/2; j++) {
                createFixture(
                        clubStats.get(j),
                        clubStats.get(clubStats.size() - 1 - j),
                        formation,
                        formation,
                        season,
                        matchDates.get(random.nextInt(12)).plusDays((i) * 7), i + 1);
                createFixture(
                        clubStats.get(clubStats.size() - 1 - j),
                        clubStats.get(j),
                        formation,
                        formation,
                        season,
                        matchDates.get(random.nextInt(12)).plusDays((i+clubStats.size()-1) * 7), (i + 1) + clubStats.size() - 1);
            }
            ClubStat temp = clubStats.get(1);
            clubStats.remove(1);
            clubStats.add(temp);
        }
    }

    @Override
    public void createFixture(ClubStat home, ClubStat away, Formation homeFormation, Formation awayFormation,
            Season season, LocalDateTime matchDate, Integer week) throws DataNotFoundException {
        Match match = new Match();
        match.setHomeClubStat(home);
        match.setAwayClubStat(away);
        match.setHomeFormation(homeFormation);
        match.setAwayFormation(awayFormation);
        match.setMatchDate(matchDate);
        match.setSeason(season);
        match.setWeek(week);
        matchRepository.save(match);
    }

    @Override
    public void deleteFixtures(List<ClubStat> clubStats, Season season) throws DataNotFoundException {
        for(ClubStat x : clubStats)
        {
            List<Match> matches = matchRepository.findByHomeClubStatAndSeason(x, season);
            if(matches == null)
                continue;
            matchRepository.deleteAll(matches);
        }
    }

    public Match getMatchById(Long matchId) {
        return matchRepository.findById(matchId).get();
    }

    @Override
    public void createFollower(Long matchId, String username) {
        Match match = matchRepository.findById(matchId).get();
        User user = userRepository.findByUsername(username).get();
        match.getUsers().add(user);
        user.getMatches().add(match);
        matchRepository.save(match);
        userRepository.save(user);
    }

    @Override
    public void deleteFollower(Long matchId, String username) {
        Match match = matchRepository.findById(matchId).get();
        User user = userRepository.findByUsername(username).get();
        match.getUsers().remove(user);
        user.getMatches().remove(match);
        matchRepository.save(match);
        userRepository.save(user);
    }
}
