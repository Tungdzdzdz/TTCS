package com.example.project1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.DTO.MatchDetailDTO;
import com.example.project1.Model.Club;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Event;
import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Season;
import com.example.project1.Model.Squad;
import com.example.project1.Response.Statistics;
import com.example.project1.repository.ClubStatRepository;
import com.example.project1.repository.EventRepository;
import com.example.project1.repository.MatchDetailRepository;
import com.example.project1.repository.MatchRepository;
import com.example.project1.repository.PlayerStatRepository;
import com.example.project1.repository.SeasonRepository;
import com.example.project1.repository.SquadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchDetailService implements IMatchDetailService {
    private final MatchDetailRepository matchDetailRepository;
    private final MatchRepository matchRepository;
    private final PlayerStatRepository playerStatRepository;
    private final ClubStatRepository clubStatRepository;
    private final EventRepository eventRepository;
    private final SquadRepository squadRepository;
    private final SeasonRepository seasonRepository;

    @Override
    public void createMatchDetail(Long matchId, MatchDetailDTO matchDetailDTO) {
        Match match = matchRepository.findById(matchId).get();
        Event event = eventRepository.findById(matchDetailDTO.getType()).get();
        if(event.getName().equals("Full-Time") || event.getName().equals("Half-Time"))
        {
            MatchDetail matchDetail = createNewEventMatch(null, null, event, match, matchDetailDTO.getMinute());
            matchDetailRepository.save(matchDetail);
            return;
        }
        PlayerStat playerStat = playerStatRepository.findById(matchDetailDTO.getPlayerStat()).get();
        ClubStat clubStat = clubStatRepository.findById(matchDetailDTO.getClubStat()).get();
        MatchDetail matchDetail = createNewEventMatch(playerStat, clubStat, event, match, matchDetailDTO.getMinute());
        matchDetailRepository.save(matchDetail);
    }

    private MatchDetail createNewEventMatch(PlayerStat playerStat, ClubStat clubStat, Event event, Match match, int eventTime) {
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setPlayerStat(playerStat);
        matchDetail.setClubStat(clubStat);
        matchDetail.setEvent(event);
        matchDetail.setEventTime(eventTime);
        matchDetail.setMatch(match);
        return matchDetail;
    }

    @Override
    public List<String> getResultByWeek(int week, int seasonId) {
        Season season = seasonRepository.findById(seasonId).get();
        List<Match> matches = matchRepository.findMatchByWeekAndSeasonOrderByMatchDate(week, season);
        Event goalEvent = eventRepository.findById(1).get();
        List<String> results = new ArrayList<>();
        for(int i = 0; i < matches.size(); i++)
        {
            int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(matches.get(i), goalEvent, matches.get(i).getHomeClubStat());
            int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(matches.get(i), goalEvent, matches.get(i).getAwayClubStat());
            String result = homeGoal + " - " + awayGoal;
            results.add(result);
        }
        return results;
    }

    @Override
    public List<String> getLastResult(List<Long> matchesId){
        Event goalEvent = eventRepository.findById(1).get();
        List<String> results = new ArrayList<>();
        for(int i = 0; i < matchesId.size(); i++)
        {
            Match match = matchRepository.findById(matchesId.get(i)).get();
            int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, match.getHomeClubStat());
            int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, match.getAwayClubStat());
            String result = homeGoal + " - " + awayGoal;
            results.add(result);
        }
        return results;
    }

    @Override
    public List<String> getResultMatchByClubStat(int clubStatId, int limit) {
        ClubStat clubStat = clubStatRepository.findById(clubStatId).get();
        List<Match> matches = matchRepository.findLastResultMatchByClubStat(clubStatId, limit);
        Event goalEvent = eventRepository.findById(1).get();
        Event fullTimeEvent = eventRepository.findById(5).get();
        System.out.println(fullTimeEvent.getName());
        List<String> results = new ArrayList<>();
        for(int i = 0; i < matches.size(); i++)
        {
            if(matchDetailRepository.countByMatchAndEventAndClubStat(matches.get(i), fullTimeEvent, null) > 0)
            {
                int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(matches.get(i), goalEvent, matches.get(i).getHomeClubStat());
                int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(matches.get(i), goalEvent, matches.get(i).getAwayClubStat());
                String result = homeGoal + " - " + awayGoal;
                results.add(result);
            }
        }
        return results;
    }

    @Override
    public String getResultByMatch(Long matchId) {
        Match match = matchRepository.findById(matchId).get();
        Event goalEvent = eventRepository.findById(1).get();
        int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, match.getHomeClubStat());
        int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, match.getAwayClubStat());
        String result = homeGoal + " - " + awayGoal;
        return result;
    }

    @Override
    public List<MatchDetail> getMatchDetailByMatch(Long matchId) {
        Match match = matchRepository.findById(matchId).get();
        return matchDetailRepository.findByMatchOrderByEventTimeAsc(match);
    }

    @Override
    public Statistics getStatisticByMatch(Long matchId) {
        Match match = matchRepository.findById(matchId).get();
        ClubStat homeClubStat = match.getHomeClubStat();
        ClubStat awayClubStat = match.getAwayClubStat();

        Statistics statistics = new Statistics();

        List<Integer> yellowCard = new ArrayList<>();
        List<Integer> redCard = new ArrayList<>();
        List<Integer> offside = new ArrayList<>();
        List<Integer> saves = new ArrayList<>();
        List<Integer> shot = new ArrayList<>();
        List<Integer> foul = new ArrayList<>();

        int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Goal"), homeClubStat);
        int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Goal"), awayClubStat);

        shot.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Shot"), homeClubStat) + homeGoal);
        shot.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Shot"), awayClubStat) + awayGoal);

        yellowCard.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Yellow Card"), homeClubStat));
        yellowCard.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Yellow Card"), awayClubStat));

        redCard.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Red Card"), homeClubStat));
        redCard.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Red Card"), awayClubStat));

        offside.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Offside"), homeClubStat));
        offside.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Offside"), awayClubStat));

        saves.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Save"), homeClubStat));
        saves.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Save"), awayClubStat));

        foul.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Foul"), homeClubStat));
        foul.add(matchDetailRepository.countByMatchAndEventAndClubStat(match, eventRepository.findByName("Foul"), awayClubStat));

        statistics.setYellowCard(yellowCard);
        statistics.setRedCard(redCard);
        statistics.setOffside(offside);
        statistics.setSaves(saves);
        statistics.setShot(shot);
        statistics.setFoul(foul);

        return statistics;
    }

}
