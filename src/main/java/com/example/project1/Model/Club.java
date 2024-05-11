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
@Table(name = "clubs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String shortName;
    private String logo;
    private String stadiumName;
    private String founded;
    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "club")
    private List<PlayerStat> players;

    @JsonIgnoreProperties({"club", "season"})
    public List<PlayerStat> getPlayers() {
        return players;
    }
}
