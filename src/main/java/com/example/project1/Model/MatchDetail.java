package com.example.project1.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "match_details")
public class MatchDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Match match;
    @ManyToOne
    private Event event;
    @ManyToOne
    private PlayerStat playerStat;
    @ManyToOne
    private ClubStat clubStat;
    private int eventTime;
}
