package com.example.project1.service;

import com.example.project1.DTO.MatchDTO;
import com.example.project1.Exception.DataNotFoundException;

public interface IMatchService {
    public void createMatch(MatchDTO matchDTO) throws DataNotFoundException;
}
