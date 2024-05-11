package com.example.project1.service;

import java.util.List;

import com.example.project1.DTO.SquadDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Squad;

public interface ISquadService {
    List<Squad> getSquadByClubStatAndMatch(Integer clubStatId, Long matchId) throws DataNotFoundException;
    void updateSquads(List<SquadDTO> squads) throws DataNotFoundException;
    void createSquad(SquadDTO squadDTO) throws DataNotFoundException;
    void updateSquad(SquadDTO squadDTO) throws DataNotFoundException;
    List<Squad> getSquadInField(Long matchId, boolean inField, Integer clubStatId) throws DataNotFoundException;
    List<Squad> getSubSquad(Long matchId, boolean type, boolean inField, Integer clubStatId) throws DataNotFoundException;
    List<Squad> getSquadByMatch(Long matchId) throws DataNotFoundException;
}
