package com.example.project1.service;

import com.example.project1.DTO.SeasonDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Season;

import java.util.List;

public interface ISeasonService {
    List<Season> getAllSeasons();
    int getCurrentWeek(int seasonId) throws DataNotFoundException;
    Season getSeasonById(int id) throws DataNotFoundException;
    void createSeason(SeasonDTO seasonDTO) throws DataNotFoundException, Exception;
    void deleteSeason(Integer id) throws DataNotFoundException;
    void updateSeason(SeasonDTO seasonDTO) throws DataNotFoundException, Exception;
}
