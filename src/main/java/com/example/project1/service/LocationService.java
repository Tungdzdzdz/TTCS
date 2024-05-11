package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Model.Location;
import com.example.project1.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService{
    private final LocationRepository locationRepository;
    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    
}
