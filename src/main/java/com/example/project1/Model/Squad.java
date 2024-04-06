package com.example.project1.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "squads")
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Match match;
    @ManyToOne
    private PlayerStat playerStat;
    @ManyToOne
    private ClubStat clubStat;
    private boolean type;
}
