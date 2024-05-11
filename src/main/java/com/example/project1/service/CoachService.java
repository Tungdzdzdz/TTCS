package com.example.project1.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.DTO.CoachDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Coach;
import com.example.project1.Model.Country;
import com.example.project1.Model.Season;
import com.example.project1.repository.CoachRepository;
import com.example.project1.repository.CountryRepository;
import com.example.project1.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachService implements ICoachService{
    private final CoachRepository coachRepository;
    private final CountryRepository countryRepository;
    @Override
    public List<Coach> getAllCoach() {
        return coachRepository.findAll();
    }
    @Override
    public List<Coach> getAllCoachBySeason(int seasonId) throws DataNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCoachBySeason'");
    }
    @Override
    public void createCoach(CoachDTO coachDTO) throws DataNotFoundException {
        coachRepository.save(mapper(coachDTO));
    }
    @Override
    public void updateCoach(CoachDTO coachDTO) throws DataNotFoundException {
        Coach coach = coachRepository.findById(coachDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("Coach not found"));
        Country country = countryRepository.findById(coachDTO.getCountry())
                .orElseThrow(() -> new DataNotFoundException("Country not found"));
        coach.setName(coachDTO.getName());
        coach.setImg(coachDTO.getImg());
        coach.setCountry(country);
        coach.setDateOfBirth(LocalDate.parse(coachDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        coachRepository.save(coach);
    }
    @Override
    public void deleteCoach(Integer coachId) throws DataNotFoundException {
        coachRepository.deleteById(coachId);
    }
    public Coach mapper(CoachDTO coachDTO) throws DataNotFoundException {
        Country country = countryRepository.findById(coachDTO.getCountry())
                .orElseThrow(() -> new DataNotFoundException("Country not found"));
        Coach coach = new Coach();
        coach.setName(coachDTO.getName());
        coach.setImg(coachDTO.getImg());
        coach.setCountry(country);
        coach.setDateOfBirth(LocalDate.parse(coachDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        coach.setGender(true);
        return coach;
    }
}
