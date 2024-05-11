package com.example.project1.service;

import com.example.project1.DTO.PlayerDTO;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.Country;
import com.example.project1.Model.Player;
import com.example.project1.repository.CountryRepository;
import com.example.project1.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {
    private final CountryRepository countryRepository;
    private final PlayerRepository playerRepository;

    public void createPlayers(List<PlayerDTO> playerList) throws DataNotFoundException {
        // for (PlayerDTO playerDTO : playerList) {
        //     Country country = countryRepository.findById(playerDTO.getCountry())
        //             .orElseThrow(() -> new DataNotFoundException("Country not found"));
        //     Player player = Player.builder()
        //             .name(playerDTO.getName())
        //             .dateOfBirth(playerDTO.getDateOfBirth())
        //             .gender(playerDTO.isGender())
        //             .height(playerDTO.getHeight())
        //             .weight(playerDTO.getWeight())
        //             .img(playerDTO.getImg())
        //             .build();
        //     player.setCountry(country);
        //     playerRepository.save(player);
        // }
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public void createPlayer(PlayerDTO playerDTO) throws Exception {
        playerRepository.save(mapper(playerDTO));
    }

    public Player mapper(PlayerDTO playerDTO) throws DataNotFoundException {
        Country country = countryRepository.findById(playerDTO.getCountry())
                .orElseThrow(() -> new DataNotFoundException("Country not found"));
        LocalDate date = LocalDate.parse(playerDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Player player = Player.builder()
                .name(playerDTO.getName())
                .dateOfBirth(date)
                .gender(true)
                .height(playerDTO.getHeight())
                .weight(playerDTO.getWeight())
                .img(playerDTO.getImg())
                .country(country)
                .build();
        return player;
    }

    @Override
    public void updatePlayer(PlayerDTO playerDTO) throws Exception {
        Player player = playerRepository.findById(playerDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("Player not found"));
        Country country = countryRepository.findById(playerDTO.getCountry())
                .orElseThrow(() -> new DataNotFoundException("Country not found"));
        player.setName(playerDTO.getName());
        player.setDateOfBirth(LocalDate.parse(playerDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        player.setHeight(playerDTO.getHeight());
        player.setWeight(playerDTO.getWeight());
        player.setCountry(country);
        player.setImg(playerDTO.getImg());
        playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Integer id) throws Exception {
        playerRepository.deleteById(id);
    }

    @Override
    public Player findPlayerById(Integer id) throws Exception {
        return playerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Player not found"));
    }
}
