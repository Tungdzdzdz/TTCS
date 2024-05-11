package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Match;
import com.example.project1.Model.Season;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{
    List<Match> findMatchByWeekAndSeasonOrderByMatchDate(int week, Season season);
    @Query(nativeQuery = true, value = "SELECT * FROM matches m WHERE (m.match_date) >= SYSDATETIME() AND season_id = ?2 ORDER BY m.match_date OFFSET 0 ROWS FETCH NEXT ?1 ROWS ONLY")
    List<Match> findNextMatch(int limit, int seasonId);
    @Query(nativeQuery = true, value = "SELECT * FROM matches m WHERE (m.match_date) <= SYSDATETIME() AND season_id = ?2 ORDER BY m.match_date OFFSET 0 ROWS FETCH NEXT ?1 ROWS ONLY")
    List<Match> findLastResultMatch(int limit, int seasonId);
    @Query(nativeQuery = true, value = "SELECT * FROM matches m WHERE (m.match_date) >= SYSDATETIME() AND (home_club_stat_id = ?1 OR away_club_stat_id = ?1) ORDER BY m.match_date")
    List<Match> findNextMatchByClubStat(int clubStatId);
    @Query(nativeQuery = true, value = "SELECT * FROM matches m WHERE (m.match_date) <= SYSDATETIME() AND (home_club_stat_id = ?1 OR away_club_stat_id = ?1) ORDER BY m.match_date")
    List<Match> findLastResultMatchByClubStat(int clubStatId, int limit);
    List<Match> findByHomeClubStatAndSeason(ClubStat homeClubStat, Season season);
}
