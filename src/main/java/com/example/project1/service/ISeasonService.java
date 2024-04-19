package com.example.project1.service;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Season;

import java.util.List;

public interface ISeasonService {
    List<Season> getAllSeasons();
    int getCurrentWeek() throws DataNotFoundException;
}
