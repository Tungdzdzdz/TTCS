package com.example.project1.service;

import com.example.project1.DTO.PlayerDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Country;
import com.example.project1.Model.Player;
import com.example.project1.repository.CountryRepository;
import com.example.project1.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService{
    private final CountryRepository countryRepository;
    private final PlayerRepository playerRepository;
    public void createPlayers(List<PlayerDTO> playerList) throws DataNotFoundException {
        for(PlayerDTO playerDTO : playerList) {
            if(!countryRepository.existsByName(playerDTO.getCountryName()))
            {
                countryRepository.save(Country.builder().name(playerDTO.getCountryName()).build());
            }
            Country country = countryRepository.findByName(playerDTO.getCountryName())
                    .orElseThrow(() -> new DataNotFoundException("Country not found"));
            Player player = Player.builder()
                    .name(playerDTO.getName())
                    .dateOfBirth(playerDTO.getDateOfBirth())
                    .gender(playerDTO.isGender())
                    .height(playerDTO.getHeight())
                    .weight(playerDTO.getWeight())
                    .img(playerDTO.getImg())
                    .build();
            player.setCountry(country);
            playerRepository.save(player);
        }
    }
}
