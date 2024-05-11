package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.project1.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {
    private final EventService eventService;
    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
}
