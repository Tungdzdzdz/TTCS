package com.example.project1.repository;

import com.example.project1.Model.Club;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubStatRepository extends JpaRepository<ClubStat, Integer>
{
    List<ClubStat> findBySeason(Season season);
    Optional<ClubStat> findByClubAndSeason(Club club, Season season);
    List<ClubStat> findBySeasonOrderByRank(Season season);
    void deleteByClubAndSeason(Club club, Season season);
    boolean existsByClub(Club club);
}
