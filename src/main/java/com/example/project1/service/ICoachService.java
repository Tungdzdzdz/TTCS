package com.example.project1.service;

import java.util.List;

import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Coach;

public interface ICoachService {
    List<Coach> getAllCoachBySeason(int seasonId) throws DataNotFoundException;
}