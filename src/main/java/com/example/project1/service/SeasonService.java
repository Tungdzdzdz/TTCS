package com.example.project1.service;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.Season;
import com.example.project1.repository.MatchDetailRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService implements ISeasonService{
    private final SeasonRepository seasonRepository;
    private final MatchRepository matchRepository;
    private final MatchDetailRepository matchDetailRepository;
    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }
    @Override
    public int getCurrentWeek() throws DataNotFoundException 
    {
        MatchDetail lastMatchDetail = matchDetailRepository
                                        .findLastMatchDetail()
                                        .orElseThrow(() -> new DataNotFoundException("The match detail is not found!"));
        return lastMatchDetail.getMatch().getWeek();
    }
}
