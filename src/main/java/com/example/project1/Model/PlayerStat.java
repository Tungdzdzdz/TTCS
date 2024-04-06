package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "player_stats")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Player player;
    @ManyToOne
    private Club club;
    @ManyToOne
    private Season season;
    private int appearance;
    private int goal;
    private int assist;
    private int cleanSheet;
    @ManyToOne
    private Position position;
    private int yellowCard;
    private int redCard;
    private int numberJersey;
}
