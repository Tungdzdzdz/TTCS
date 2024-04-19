package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "squads")
@Data
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
    private boolean inField;
}
