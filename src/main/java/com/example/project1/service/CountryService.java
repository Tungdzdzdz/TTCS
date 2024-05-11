package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Model.Country;
import com.example.project1.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService implements ICountryService{
    private final CountryRepository countryRepository;
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    
}
