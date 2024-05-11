package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.service.CountryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/country")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    @GetMapping
    public ResponseEntity<?> getAllCountries()
    {
        return ResponseEntity.ok().body(countryService.getAllCountries());
    }
}
