package com.example.project1.service;

import com.example.project1.DTO.ClubDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Club;
import com.example.project1.Model.Location;
import com.example.project1.repository.ClubRepository;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService implements IClubService{
    private final ClubRepository clubRepository;
    private final LocationRepository locationRepository;
    private final ClubStatRepository clubStatRepository;
    @Override
    public void createClub(ClubDTO clubDTO) throws Exception {
        clubRepository.save(mapper(clubDTO));
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

    @Override
    public void updateClub(ClubDTO clubDTO) throws DataNotFoundException {
        Club club = clubRepository
                .findById(clubDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("Club not found"));
        club.setName(clubDTO.getName());
        club.setLogo(clubDTO.getLogo());
        club.setShortName(clubDTO.getShortName());
        club.setStadiumName(clubDTO.getStadiumName());
        club.setFounded(clubDTO.getFounded());
        club.setLocation(locationRepository
                .findById(clubDTO.getLocation())
                .orElseThrow(() -> new DataNotFoundException("Location not found")));
        clubRepository.save(club);
    }

    private Club mapper(ClubDTO clubDTO) throws DataNotFoundException {
        Location location = locationRepository
                .findById(clubDTO.getLocation())
                .orElseThrow(() -> new DataNotFoundException("The location is not found!"));
        return Club
                .builder()
                .name(clubDTO.getName())
                .logo(clubDTO.getLogo())
                .shortName(clubDTO.getShortName())
                .stadiumName(clubDTO.getStadiumName())
                .founded(clubDTO.getFounded())
                .location(location)
                .build();
    }

    @Override
    public void deleteClub(Integer clubId) throws DataNotFoundException {
        Club club = clubRepository
                .findById(clubId)
                .orElseThrow(() -> new DataNotFoundException("Club not found"));
        if(clubStatRepository.existsByClub(club))
            throw new DataNotFoundException("Club cannot be deleted because it has club stats");
        clubRepository.delete(club);
    }
}
