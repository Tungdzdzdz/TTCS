package com.example.project1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.DTO.MatchDetailDTO;
import com.example.project1.Model.ClubStat;
import com.example.project1.Model.Event;
import com.example.project1.Model.Match;
import com.example.project1.Model.MatchDetail;
import com.example.project1.Model.PlayerStat;
import com.example.project1.Model.Season;
import com.example.project1.Model.Squad;
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
    public void createMatchDetail(MatchDetailDTO matchDetailDTO) {
        int numEvent = (int) (Math.random() * 40) + 1;
        ClubStat homeClubStat = clubStatRepository.findById(matchDetailDTO.getHomeClubStatId()).get();
        ClubStat awayClubStat = clubStatRepository.findById(matchDetailDTO.getAwayClubStatId()).get();
        ClubStat[] clubs = { homeClubStat, awayClubStat };
        Match match = matchRepository.findById(matchDetailDTO.getMatchId()).get();
        for (int i = 0; i < numEvent; i++) {
            int club = (int) (Math.random() * 2);
            int eventId = (int) (Math.random() * 12) + 1;
            int eventTime = (int) (Math.random() * 90) + 1;
            if (eventId == 8) {
                eventId = 1;
            } else if (eventId == 6) {
                eventId = 7;
            } else if (eventId == 10) {
                eventId = 9;
            }
            else if(eventId == 4 || eventId == 5)
                continue;
            Event event = eventRepository.findById(eventId).get();
            Squad squad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 1) % 2].getId(), true);
            switch (eventId) {
                case 1:
                    matchDetailRepository.save(createNewEventMatch(squad.getPlayerStat(), clubs[club], event, match, eventTime));
                    int assist = (int) (Math.random() * 2);
                    if (assist == 1) {
                        Squad assistSquad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 1) % 2].getId(), true);
                        while (assistSquad.equals(squad)) {
                            assistSquad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 1) % 2].getId(), true);
                        }
                        matchDetailRepository.save(
                                createNewEventMatch(assistSquad.getPlayerStat(), clubs[(club + 1) % 2], eventRepository.findById(8).get(), match, eventTime));
                    }
                    break;
                case 7:
                    matchDetailRepository.save(createNewEventMatch(squad.getPlayerStat(), clubs[club], event, match, eventTime));
                    Squad inSquad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 1) % 2].getId(), false);
                    while (inSquad.equals(squad)) {
                        inSquad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 1) % 2].getId(), false);
                    }
                    matchDetailRepository.save(
                            createNewEventMatch(inSquad.getPlayerStat(), clubs[(club + 1) % 2], eventRepository.findById(6).get(), match, eventTime));
                    break;
                case 9:
                    matchDetailRepository.save(createNewEventMatch(squad.getPlayerStat(), clubs[(club+1)%2], event, match, eventTime));
                    int save = (int) (Math.random() * 2);
                    if (save == 1) {
                        Squad saveSquad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 2) % 2].getId(), true);
                        while (!saveSquad.getPlayerStat().getPosition().getShortName().equals("GK")) {
                            saveSquad = squadRepository.findOneRandomByClubStatAndInField(clubs[(club + 2) % 2].getId(), true);
                        }
                        matchDetailRepository.save(
                                createNewEventMatch(saveSquad.getPlayerStat(), clubs[(club + 2) % 2], eventRepository.findById(10).get(), match, eventTime));
                    }
                    break;
                default:
                    matchDetailRepository.save(createNewEventMatch(squad.getPlayerStat(), clubs[(club+1)%2], event, match, eventTime));
                    break;
            }
        }
        matchDetailRepository.save(createNewEventMatch(null, null, eventRepository.findById(4).get(), match, 45)); 
        matchDetailRepository.save(createNewEventMatch(null, null, eventRepository.findById(5).get(), match, 90)); 
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
}
