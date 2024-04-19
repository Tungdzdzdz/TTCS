package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.Model.Coach;
import com.example.project1.Model.Season;

public interface CoachRepository extends JpaRepository<Coach, Integer>{
}
