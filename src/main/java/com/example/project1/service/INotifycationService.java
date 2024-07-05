package com.example.project1.service;

import java.util.List;

import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.Notifycation;
import com.example.project1.Model.User;

public interface INotifycationService {
    Notifycation createNotifycation(Match match, User user, MatchDetail matchDetail);  
    Notifycation createNotifycation(Match match, User user, String message, String title); 
    List<Notifycation> getNotifycationByUser(Integer userId, Integer from);
    void seen(String username);
}
