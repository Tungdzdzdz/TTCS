package com.example.project1.repository;

import com.example.project1.Model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {
    @Query("SELECT s FROM Season s WHERE YEAR(s.startSeason) = :year")
    Optional<Season> findByStartSeasonWithYearOfStartSeasonEqualsStartYear(@Param("year") int year);
    List<Season> findAll();
    Optional<Season> findById(int id);
    Season findFirstByOrderByIdDesc();
}
