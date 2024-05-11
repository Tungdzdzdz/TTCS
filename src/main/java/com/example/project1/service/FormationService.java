package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Model.Formation;
import com.example.project1.repository.FormationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormationService implements IFormationService{
    private final FormationRepository formationRepository;

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }  
}
