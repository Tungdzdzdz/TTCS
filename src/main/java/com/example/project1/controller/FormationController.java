package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.service.FormationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/formation")
@CrossOrigin("http://localhost:5173")
public class FormationController {
    private final FormationService formationService;

    @GetMapping
    public ResponseEntity<?> getAllFormations() {
        return ResponseEntity.ok().body(formationService.getAllFormations());
    }
}
