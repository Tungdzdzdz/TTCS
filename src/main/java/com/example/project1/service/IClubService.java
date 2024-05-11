package com.example.project1.service;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;

import java.util.List;

public interface IClubService {
    void createClub(ClubDTO clubDTO) throws Exception;
    List<Club> getAllClubs();
    Club getClubById(int clubId) throws DataNotFoundException;
    void updateClub(ClubDTO clubDTO) throws DataNotFoundException;
    void deleteClub(Integer clubId) throws DataNotFoundException;
}
