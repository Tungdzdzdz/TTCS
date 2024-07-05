package com.example.project1.service;

import java.util.List;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubCoach;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Season;

public interface IClubCoachService {
    List<ClubCoach> getClubCoachBySeason(int seasonId);
    ClubCoach getClubCoachRandom(int seasonId);
    Coach getCoachByClubAndSeason(int clubId, int seasonId) throws DataNotFoundException;
    ClubCoach getClubCoachByCoachAndSeason(int coachId, int seasonId) throws DataNotFoundException;
    List<Season> getSeasonByCoach(int coachId);
    // void createClubCoach(int seasonId);
}
