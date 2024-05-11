package com.example.project1.service;

import com.example.project1.DTO.PlayerDTO;
import com.example.project1.Model.Player;

import java.util.List;

public interface IPlayerService {
    void createPlayers(List<PlayerDTO> playerList) throws Exception;
    List<Player> getAllPlayers();
    void createPlayer(PlayerDTO playerDTO) throws Exception;
    void updatePlayer(PlayerDTO playerDTO) throws Exception;
    void deletePlayer(Integer id) throws Exception;
    Player findPlayerById(Integer id) throws Exception;
}
