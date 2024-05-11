package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/location")
@CrossOrigin("http://localhost:5173")
public class LocationController {
    private final LocationService locationService;
    
    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
