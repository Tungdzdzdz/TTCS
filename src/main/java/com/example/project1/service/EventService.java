package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Model.Event;
import com.example.project1.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {
    private final EventRepository eventRepository;
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
}
