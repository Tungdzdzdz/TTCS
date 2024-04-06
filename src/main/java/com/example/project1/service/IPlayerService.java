package com.example.project1.service;

import com.example.project1.DTO.PlayerDTO;

import java.util.List;

public interface IPlayerService {
    void createPlayers(List<PlayerDTO> playerList) throws Exception;
}
