package com.example.project1.service;

import java.util.List;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.ClubCoach;
import com.example.project1.Model.Coach;

public interface IClubCoachService {
    List<ClubCoach> getClubCoachBySeason(int seasonId);
    ClubCoach getClubCoachRandom(int seasonId);
    Coach getCoachByClubAndSeason(int clubId, int seasonId) throws DataNotFoundException;
    // void createClubCoach(int seasonId);
}
