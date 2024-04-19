package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "club_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Club club;
    @ManyToOne
    private Season season;
    private int goalTaken;
    private int goalReceived;
    private int cleanSheet;
    private int yellowCard;
    private int redCard;
    private int win;
    private int draw;
    private int lose;
    private int point;
    private int rank;
    private int matchNumber;
    private int offside;
    private int saves;
    private int shot;
    private int foul;
}
