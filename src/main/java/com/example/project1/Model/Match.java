package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private ClubStat homeClubStat;
    @ManyToOne
    private ClubStat awayClubStat;
    @ManyToOne
    @JoinColumn(name = "home_formation_id")
    private Formation homeFormation;
    @ManyToOne
    @JoinColumn(name = "away_formation_id")
    private Formation awayFormation;
    private LocalDateTime matchDate;
    private int week;
    @ManyToOne
    private Season season;

    @ManyToMany(mappedBy = "matches")
    private List<User> users;

    @JsonIgnoreProperties({"matches"})
    public List<User> getUsers() {
        return users;
    }
}
