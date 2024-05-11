package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Model.Position;
import com.example.project1.repository.PositionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionService implements IPositionService{
    private final PositionRepository positionRepository;

    @Override
    public List<Position> getPosition() {
        return positionRepository.findAll();
    }
}
