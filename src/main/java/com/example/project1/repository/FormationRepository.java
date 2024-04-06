package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Integer>{
    
}
