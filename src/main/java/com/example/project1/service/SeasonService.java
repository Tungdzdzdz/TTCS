package com.example.project1.service;

import com.example.project1.Model.Season;
import com.example.project1.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService implements ISeasonService{
    private final SeasonRepository seasonRepository;
    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }
}
