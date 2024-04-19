package com.example.project1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Event;
import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;

@Repository
public interface MatchDetailRepository extends JpaRepository<MatchDetail, Long>{
    public int countByMatchAndEventAndClubStat(Match match, Event event, ClubStat clubStat);
    public boolean existsByMatch(Match match);
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM match_details ORDER BY id DESC")
    public Optional<MatchDetail> findLastMatchDetail();
}
