package com.example.project1.service;

import java.util.List;

import com.example.project1.DTO.CoachDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Coach;

public interface ICoachService {
    List<Coach> getAllCoachBySeason(int seasonId) throws DataNotFoundException;
    List<Coach> getAllCoach();
    void createCoach(CoachDTO coachDTO) throws DataNotFoundException;
    void updateCoach(CoachDTO coachDTO) throws DataNotFoundException;
    void deleteCoach(Integer coachId) throws DataNotFoundException;
}