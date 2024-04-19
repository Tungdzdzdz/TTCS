package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Model.ClubCoach;
import com.example.project1.repository.ClubCoachRepository;
import com.example.project1.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubCoachService implements IClubCoachService{
    private final ClubCoachRepository clubCoachRepository;
    private final SeasonRepository seasonRepository;
    @Override
    public List<ClubCoach> getClubCoachBySeason(int seasonId) {
        return clubCoachRepository.findBySeason(seasonRepository.findById(seasonId).get());
    }
    @Override
    public ClubCoach getClubCoachRandom(int seasonId) {
        return clubCoachRepository.findRandomBySeason(seasonId);
    }
}
