package com.example.project1.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "formations")
@Data
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
