package com.example.project1.Response;

import java.util.List;

import lombok.Data;

@Data
public class Statistics {
    private List<Integer> yellowCard;
    private List<Integer> redCard;
    private List<Integer> offside;
    private List<Integer> saves;
    private List<Integer> shot;
    private List<Integer> foul;
}
