package com.example.project1.repository;

import com.example.project1.Model.Club;
import com.example.project1.Model.Player;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStatRepository extends JpaRepository<PlayerStat, Integer> {
    List<PlayerStat> findPlayerStatBySeason(Season season);
    List<PlayerStat> findPlayerStatByClubAndSeason(Club club, Season season);
    Optional<PlayerStat> findByPlayerAndSeason(Player player, Season season);
}
