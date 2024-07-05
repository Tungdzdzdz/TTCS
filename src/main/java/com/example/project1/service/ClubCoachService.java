package com.example.project1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubCoach;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Season;
import com.example.project1.repository.ClubCoachRepository;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.CoachRepository;
import com.example.project1.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubCoachService implements IClubCoachService{
    private final ClubCoachRepository clubCoachRepository;
    private final SeasonRepository seasonRepository;
    private final ClubRepository clubRepository;
    private final CoachRepository coachRepository;
    // private final ClubRepository clubRepository;
    // private final CoachRepository coachRepository;
    @Override
    public List<ClubCoach> getClubCoachBySeason(int seasonId) {
        return clubCoachRepository.findBySeason(seasonRepository.findById(seasonId).get());
    }
    @Override
    public ClubCoach getClubCoachRandom(int seasonId) {
        return clubCoachRepository.findRandomBySeason(seasonId);
    }
    @Override
    public Coach getCoachByClubAndSeason(int clubId, int seasonId) throws DataNotFoundException {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new DataNotFoundException("Club not found"));
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new DataNotFoundException("Season not found"));
        List<ClubCoach> clubCoach = clubCoachRepository.findByClubAndSeason(club, season);
        return clubCoach.get(0).getCoach(); 
    }
    @Override
    public ClubCoach getClubCoachByCoachAndSeason(int coachId, int seasonId) throws DataNotFoundException {
        Coach coach = coachRepository.findById(coachId).orElseThrow(() -> new DataNotFoundException("Coach not found"));
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new DataNotFoundException("Season not found"));
        ClubCoach clubCoach = clubCoachRepository.findByCoachAndSeason(coach, season);
        return clubCoach;   
    }
    @Override
    public List<Season> getSeasonByCoach(int coachId) {
        Coach coach = coachRepository.findById(coachId).get();
        List<ClubCoach> clubCoach = clubCoachRepository.findByCoach(coach);
        return clubCoach.stream().map(e -> e.getSeason()).toList();
    }
    // @Override
    // public void createClubCoach(int seasonId) {
    //     Season season = seasonRepository.findById(seasonId).get();
    //     List<Club> clubs = clubRepository.findAll();
    //     List<Coach> coaches = coachRepository.findAll();  
    //     for(int i = 0; i < coaches.size(); i++)
    //     {
    //         ClubCoach clubCoach = new ClubCoach();
    //         clubCoach.setSeason(season);
    //         clubCoach.setClub(clubs.get(i));
    //         clubCoach.setCoach(coaches.get(i));
    //         clubCoachRepository.save(clubCoach);
    //     }  
    // }
}
