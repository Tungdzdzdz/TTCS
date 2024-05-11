package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Table(name = "seasons")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate startSeason;
    private LocalDate endSeason;
    private String sponsor;

    @OneToMany(mappedBy = "season")
    private List<ClubCoach> clubCoaches;

    @OneToMany(mappedBy = "season")
    private List<ClubStat> clubStats;

    @JsonIgnoreProperties("season")
    public List<ClubStat> getClubStats() {
        return clubStats;
    }

    @JsonIgnoreProperties({"season", "club"})
    public List<ClubCoach> getClubCoaches() {
        return clubCoaches;
    }
}
