package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{
    
}
