package com.example.project1.repository;

import com.example.project1.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>
{
    Optional<Player> findByName(String name);
}
