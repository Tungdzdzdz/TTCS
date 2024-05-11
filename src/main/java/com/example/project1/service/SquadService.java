package com.example.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.DTO.SquadDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Match;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Squad;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.PlayerStatRepository;
import com.example.project1.repository.SquadRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SquadService implements ISquadService{
    private final SquadRepository squadRepository;
    private final ClubStatRepository clubStatRepository;
    private final MatchRepository matchRepository;
    private final PlayerStatRepository playerRepository;

    @Override
    public List<Squad> getSquadByClubStatAndMatch(Integer clubStatId, Long matchId) throws DataNotFoundException {
        ClubStat clubStat = clubStatRepository.findById(clubStatId).orElseThrow(() -> new DataNotFoundException("Club Stat is not found!"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        return squadRepository.findByClubStatAndMatch(clubStat, match);
    }

    @Override
    public void updateSquads(List<SquadDTO> squads) throws DataNotFoundException {
        Match match = matchRepository.findById(squads.get(0).getMatchId()).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        List<Squad> oldSquads = squadRepository.findByMatch(match);
        for (SquadDTO squadDTO : squads) {
            if(squadDTO.getId() == null) {
                createSquad(squadDTO);
            }
            else {
                for(Squad squad : oldSquads) {
                    if(squad.getId().equals(squadDTO.getId())) {
                        oldSquads.remove(squad);
                        updateSquad(squadDTO);
                        break;
                    }
                }
            }
        }
        for(Squad squad : oldSquads) {
            squadRepository.delete(squad);
        }
    }

    @Override
    public void createSquad(SquadDTO squadDTO) throws DataNotFoundException {
        squadRepository.save(mapper(squadDTO));
    }

    @Override
    public void updateSquad(SquadDTO squadDTO) throws DataNotFoundException {
        Squad squad = squadRepository.findById(squadDTO.getId()).orElseThrow(() -> new DataNotFoundException("Squad is not found!"));
        squad.setType(squadDTO.isType());
        squadRepository.save(squad);
    }
    
    private Squad mapper(SquadDTO squadDTO) throws DataNotFoundException
    {
        Match match = matchRepository.findById(squadDTO.getMatchId()).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        ClubStat clubStat = clubStatRepository.findById(squadDTO.getClubStatId()).orElseThrow(() -> new DataNotFoundException("Club Stat is not found!"));
        PlayerStat playerStat = playerRepository.findById(squadDTO.getPlayerStatId()).orElseThrow(() -> new DataNotFoundException("Player Stat is not found!"));
        Squad squad = new Squad();
        squad.setMatch(match);
        squad.setClubStat(clubStat);
        squad.setPlayerStat(playerStat);
        squad.setType(squadDTO.isType());
        squad.setInField(false);
        return squad;
    }

    @Override
    public List<Squad> getSquadInField(Long matchId, boolean inField, Integer clubStatId)
            throws DataNotFoundException {
        ClubStat clubStat = clubStatRepository.findById(clubStatId).orElseThrow(() -> new DataNotFoundException("Club Stat is not found!"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        return squadRepository.findByMatchAndInFieldAndClubStat(match, inField, clubStat);
    }

    @Override
    public List<Squad> getSubSquad(Long matchId, boolean type, boolean inField, Integer clubStatId)
            throws DataNotFoundException {
        ClubStat clubStat = clubStatRepository.findById(clubStatId).orElseThrow(() -> new DataNotFoundException("Club Stat is not found!"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        return squadRepository.findByMatchAndTypeAndInFieldAndClubStat(match, type, inField, clubStat);
    }

    @Override
    public List<Squad> getSquadByMatch(Long matchId) throws DataNotFoundException {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new DataNotFoundException("Match is not found!"));
        return squadRepository.findByMatch(match);
    }
}
