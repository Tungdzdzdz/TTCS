package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.ClubCoach;
import com.example.project1.Model.Season;

@Repository
public interface ClubCoachRepository extends JpaRepository<ClubCoach, Integer>{
    List<ClubCoach> findBySeason(Season season);
    @Query(value = "SELECT TOP 1 * FROM club_coaches WHERE season_id = ?1 ORDER BY RAND()", nativeQuery = true)
    ClubCoach findRandomBySeason(int seasonId);
}
