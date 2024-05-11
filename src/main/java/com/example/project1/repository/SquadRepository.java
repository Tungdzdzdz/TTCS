package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Match;
import com.example.project1.Model.Squad;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long>{
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM squads WHERE club_stat_id = ?1 AND in_field = ?2 ORDER BY NEWID()")
    Squad findOneRandomByClubStatAndInField(int clubStatId, boolean inField);
    @Query(nativeQuery = true, value = "SELECT match_id FROM squads WHERE player_stat_id = ?1 AND (in_field = ?2 OR type = ?2)")
    List<Long> findAppearanceMatchByPlayerStat(int playerStatId, boolean appearance);

    List<Squad> findByClubStatAndMatch(ClubStat clubStat, Match match);
    List<Squad> findByMatch(Match match);
    List<Squad> findByMatchAndInFieldAndClubStat(Match match, boolean inField, ClubStat clubStat);
    List<Squad> findByMatchAndTypeAndInFieldAndClubStat(Match match, boolean type, boolean inField, ClubStat clubStat);
}
