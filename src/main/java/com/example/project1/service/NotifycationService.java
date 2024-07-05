package com.example.project1.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.Notifycation;
import com.example.project1.Model.User;
import com.example.project1.repository.NotifycationRepository;
import com.example.project1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifycationService implements INotifycationService{
    private final NotifycationRepository notifycationRepository;
    private final UserRepository userRepository;    
    @Override
    public Notifycation createNotifycation(Match match, User user, MatchDetail matchDetail) {
        String title = "Update match " + match.getHomeClubStat().getClub().getName() + " vs " + match.getAwayClubStat().getClub().getName();
        String message = "";
        if(matchDetail.getEvent().getName().equals("Haft-Time") || matchDetail.getEvent().getName().equals("Full-Time"))
        {
            message = matchDetail.getEvent().getName() + " at" + matchDetail.getEventTime() + " minutes";
        }
        else
        {
            message =  matchDetail.getEvent().getName() + ": " + matchDetail.getPlayerStat().getPlayer().getName() + " at " + matchDetail.getEventTime() + " minutes";
        }
        Notifycation notifycation = new Notifycation();
        notifycation.setTitle(title);
        notifycation.setMessage(message);
        notifycation.setUser(user);
        notifycation.setMatch(match);
        notifycation.setStatus(false);
        return notifycationRepository.save(notifycation);
    }
    @Override
    public List<Notifycation> getNotifycationByUser(Integer userId, Integer from) {
        return notifycationRepository.findByUserOrderByIdDesc(userId, from);
    }
    @Override
    public void seen(String username) {
        User user = userRepository.findByUsername(username).get();
        List<Notifycation> notifycations = notifycationRepository.findByUser(user);
        for(Notifycation notifycation : notifycations)
        {
            notifycation.setStatus(true);
            notifycationRepository.save(notifycation);
        }
    }
    @Override
    public Notifycation createNotifycation(Match match, User user, String message, String title) {
        Notifycation notifycation = new Notifycation();
        notifycation.setMatch(match);
        notifycation.setUser(user);
        notifycation.setMessage(message);
        notifycation.setTitle(title);
        notifycation.setStatus(false);
        return notifycationRepository.save(notifycation);
    }
}
