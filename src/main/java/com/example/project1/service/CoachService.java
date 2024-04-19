package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Season;
import com.example.project1.repository.CoachRepository;
import com.example.project1.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachService implements ICoachService{
    private final CoachRepository coachRepository;
    private final SeasonRepository seasonRepository;
    @Override
    public List<Coach> getAllCoachBySeason(int seasonId) throws DataNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCoachBySeason'");
    }
}
