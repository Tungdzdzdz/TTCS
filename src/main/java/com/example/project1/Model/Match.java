package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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
    private Formation homeFormation;
    @ManyToOne
    private Formation awayFormation;
    private LocalDateTime matchDate;
    private int week;
    @ManyToOne
    private Season season;
}
