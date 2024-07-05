package com.example.project1.service;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.DTO.ClubPlayer;
import com.example.project1.DTO.ClubStatDTO;
import com.example.project1.DTO.PlayerDTO;
import com.example.project1.DTO.SeasonDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubCoach;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.Player;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Position;
import com.example.project1.Model.Season;
import com.example.project1.repository.ClubCoachRepository;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.CoachRepository;
import com.example.project1.repository.MatchDetailRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.PlayerStatRepository;
import com.example.project1.repository.PositionRepository;
import com.example.project1.repository.SeasonRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeasonService implements ISeasonService{
    private final SeasonRepository seasonRepository;
    private final MatchRepository matchRepository;
    private final MatchDetailRepository matchDetailRepository;
    private final ClubRepository clubRepository;
    private final ClubStatService clubStatService;
    private final PlayerStatService playerStatService;
    private final PlayerService playerService;
    private final PositionRepository positionRepository;
    private final MatchService matchService;
    private final CoachRepository coachRepository;
    private final ClubCoachRepository clubCoachRepository;
    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }
    @Override
    public int getCurrentWeek(int seasonId) throws DataNotFoundException 
    {
        List<Match> match = matchRepository.findNextMatch(1, seasonId);
        if(match.size() == 0)
        {
            return 1;
        }
        return match.get(0).getWeek();
    }
    @Override
    public Season getSeasonById(int id) throws DataNotFoundException {
        return seasonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("The season is not found!"));
    }
    @Override
    public void createSeason(SeasonDTO seasonDTO) throws Exception {
        if(checkValidSeason(seasonDTO.getStartSeason()))
        {
            throw new Exception("The last season has been not finished yet!");
        }
        Season season = new Season();
        season.setName(seasonDTO.getName());
        season.setSponsor(seasonDTO.getSponsor());
        season.setStartSeason(seasonDTO.getStartSeason());
        season.setEndSeason(seasonDTO.getEndSeason());
        seasonRepository.save(season);
        ArrayList<ClubStat> clubStats = new ArrayList<>();
        for(ClubPlayer clubPlayer : seasonDTO.getClubs())
        {
            Integer clubId = clubPlayer.getClub().getId();
            Club club = clubRepository.findById(clubId).orElseThrow(() -> new DataNotFoundException("The club is not found!"));
            clubStats.add(clubStatService.createClubStat(club, season));
            for(int i = 0; i < clubPlayer.getPlayers().size(); i++)
            {
                PlayerDTO x = clubPlayer.getPlayers().get(i);
                Integer playerId = x.getId();
                Player player = playerService.findPlayerById(playerId);
                Integer positionId = clubPlayer.getPositions().get(i).getId();
                Position position = positionRepository.findById(positionId).orElseThrow(() -> new DataNotFoundException("The position is not found!"));
                Integer number = clubPlayer.getNumberJersey().get(i);
                playerStatService.createPlayerStat(player, club, season, position, number);
            }
            Coach coach = coachRepository.findById(clubPlayer.getCoach().getId()).orElseThrow(() -> new DataNotFoundException("The coach is not found!"));
            ClubCoach clubCoach = new ClubCoach();
            clubCoach.setClub(club);
            clubCoach.setCoach(coach);
            clubCoach.setSeason(season);
            clubCoachRepository.save(clubCoach);
        }
        matchService.createFixtures(clubStats, season);
    }
    @Override
    public void deleteSeason(Integer id) throws DataNotFoundException {
        seasonRepository.deleteById(id);
    }
    @Override
    public void updateSeason(SeasonDTO seasonDTO) throws Exception {
        Season season = seasonRepository.findById(seasonDTO.getId()).orElseThrow(() -> new DataNotFoundException("The season is not found!"));
        seasonRepository.delete(season);
        createSeason(seasonDTO);
    }

    private boolean checkValidSeason(LocalDate startDate)
    {
        if(seasonRepository.getByEndSeason(startDate).size() == 0)
            return false;
        return true;
    }
}
