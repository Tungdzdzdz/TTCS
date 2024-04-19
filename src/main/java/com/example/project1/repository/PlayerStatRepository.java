package com.example.project1.repository;

import com.example.project1.Model.Club;
import com.example.project1.Model.Player;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStatRepository extends JpaRepository<PlayerStat, Integer> {
    List<PlayerStat> findPlayerStatBySeason(Season season);
    List<PlayerStat> findPlayerStatByClubAndSeason(Club club, Season season);
    Optional<PlayerStat> findByPlayerAndSeason(Player player, Season season);    
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM player_stats WHERE position_id = 1 AND club_id = ?1 ORDER BY NEWID()")
    PlayerStat findRandomGoalkeeper(int clubId);
    @Query(nativeQuery = true, value = "SELECT TOP 20 * FROM player_stats WHERE player_id <> ?1 AND club_id = ?2 ORDER BY NEWID()")
    List<PlayerStat> findRandomOutfieldPlayer(int playerId, int clubId);
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM player_stats WHERE season_id = ?1 ORDER BY NEWID()")
    PlayerStat findRandomPlayerStatBySeasonId(int seasonId);
}
