package com.example.project1.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public void setDefault()
    {
        this.cleanSheet = 0;
        this.draw = 0;
        this.foul = 0;
        this.goalReceived = 0;
        this.goalTaken = 0;
        this.lose = 0;
        this.matchNumber = 0;
        this.offside = 0;
        this.point = 0;
        this.rank = 0;
        this.redCard = 0;
        this.saves = 0;
        this.shot = 0;
        this.win = 0;
        this.yellowCard = 0;
    }
}
