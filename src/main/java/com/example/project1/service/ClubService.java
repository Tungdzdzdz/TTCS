package com.example.project1.service;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.Location;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService implements IClubService{
    private final ClubRepository clubRepository;
    private final LocationRepository locationRepository;
    @Override
    public void createClub(ClubDTO clubDTO) throws Exception {
        Location location = locationRepository
                .findByName(clubDTO.getLocationName())
                .orElseThrow(() -> new DataNotFoundException("The location is not found!"));
        Club club = Club
                .builder()
                .name(clubDTO.getName())
                .logo(clubDTO.getLogo())
                .shortName(clubDTO.getShortName())
                .stadiumName(clubDTO.getStadiumName())
                .founded(clubDTO.getFounded())
                .build();
        club.setLocation(location);
        clubRepository.save(club);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public Club getClubById(int clubId) throws DataNotFoundException {
        return clubRepository
                .findById(clubId)
                .orElseThrow(() -> new DataNotFoundException("Club not found"));
    }
}
