package com.example.project1.service;

import java.util.List;

import com.example.project1.Model.ClubCoach;

public interface IClubCoachService {
    List<ClubCoach> getClubCoachBySeason(int seasonId);
    ClubCoach getClubCoachRandom(int seasonId);
}
