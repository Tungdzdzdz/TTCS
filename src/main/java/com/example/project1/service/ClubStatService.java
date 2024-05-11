package com.example.project1.service;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.DTO.ClubStatDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Season;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubStatService implements IClubStatService {
	private final ClubStatRepository clubStatRepository;
	private final ClubRepository clubRepository;
	private final SeasonRepository seasonRepository;

	@Override
	public void createClubStat(ClubStatDTO clubStatDTO) throws DataNotFoundException {
		Club club = clubRepository
				.findByName(clubStatDTO.getClubName())
				.orElseThrow(() -> new DataNotFoundException("Club not found"));
		Season season = seasonRepository
				.findById(clubStatDTO.getSeasonId())
				.orElseThrow(() -> new DataNotFoundException("Season not found"));
		ClubStat clubStat = convertFromClubStatDTO(clubStatDTO);
		clubStat.setClub(club);
		clubStat.setSeason(season);
		clubStatRepository.save(clubStat);
	}

	@Override
	public List<ClubStat> getClubStatsByStartSeasonYear(int startSeasonYear) throws DataNotFoundException {
		Season season = seasonRepository
				.findByStartSeasonWithYearOfStartSeasonEqualsStartYear(startSeasonYear)
				.orElseThrow(() -> new DataNotFoundException("Season not found"));
		return clubStatRepository.findBySeason(season);
	}

	@Override
	public List<ClubStat> getClubStatsBySeasonId(int seasonId) throws DataNotFoundException {
		Season season = seasonRepository
				.findById(seasonId)
				.orElseThrow(() -> new DataNotFoundException("Season not found"));
		return clubStatRepository.findBySeason(season);
	}

	@Override
	public ClubStat getClubStat(int clubId, int seasonId) throws DataNotFoundException {
		Club club = clubRepository
				.findById(clubId)
				.orElseThrow(() -> new DataNotFoundException("Club not found"));
		Season season = seasonRepository
				.findById(seasonId)
				.orElseThrow(() -> new DataNotFoundException("Season not found"));
		return clubStatRepository.findByClubAndSeason(club, season)
				.orElseThrow(() -> new DataNotFoundException("Club stat not found"));
	}

	private ClubStat convertFromClubStatDTO(ClubStatDTO clubStatDTO) {
		return ClubStat.builder()
				.draw(clubStatDTO.getDraw())
				.lose(clubStatDTO.getLose())
				.win(clubStatDTO.getWin())
				.goalReceived(clubStatDTO.getGoalReceived())
				.goalTaken(clubStatDTO.getGoalTaken())
				.cleanSheet(clubStatDTO.getCleanSheet())
				.yellowCard(clubStatDTO.getYellowCard())
				.redCard(clubStatDTO.getRedCard())
				.point(clubStatDTO.getPoint())
				.rank(clubStatDTO.getRank())
				.matchNumber(clubStatDTO.getMatchNumber())
				.offside(clubStatDTO.getOffside())
				.foul(clubStatDTO.getFoul())
				.saves(clubStatDTO.getSaves())
				.shot(clubStatDTO.getShot())
				.build();
	}

	@Override
	public List<ClubStat> getTableBySeason(int seasonId) throws DataNotFoundException {
		Season season = seasonRepository
				.findById(seasonId)
				.orElseThrow(() -> new DataNotFoundException("Season not found"));
		return clubStatRepository.findBySeasonOrderByRank(season);
	}

	@Override
	public ClubStat createClubStat(Club club, Season season) throws DataNotFoundException {
		ClubStat clubStat = new ClubStat();
		clubStat.setClub(club);
		clubStat.setSeason(season);
		clubStat.setDefault();
		return clubStatRepository.save(clubStat);
	}
}
