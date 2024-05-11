package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Model.Position;
import com.example.project1.service.PositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/position")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PositionController {
    private final PositionService positionService;
    
    @GetMapping()
    public ResponseEntity<?> getPostion()
    {
        return ResponseEntity.ok(positionService.getPosition());
    }
}
