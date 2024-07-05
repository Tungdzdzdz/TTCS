package com.example.project1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public MatchDetail createMatchDetail(Long matchId, MatchDetailDTO matchDetailDTO) throws Exception {
        Match match = matchRepository.findById(matchId).get();
        Event event = eventRepository.findById(matchDetailDTO.getType()).get();
        if(event.getName().equals("Full-Time") || event.getName().equals("Half-Time") || event.getName().equals("Kick-Off"))
        {
            if(matchDetailRepository.countByMatchAndEventAndClubStat(match, event, null) > 0)
            {
                throw new Exception("This event must be unique");
            }
            if(event.getName().equals("Full-Time"))
            {
                if(matchDetailRepository.countByMatchAndEventTimeGreaterThan(match, matchDetailDTO.getMinute()) > 0)
                    throw new Exception("Can't add this event");
                updateTable(null);
            }
            MatchDetail matchDetail = createNewEventMatch(null, null, event, match, matchDetailDTO.getMinute());
            return matchDetailRepository.save(matchDetail);
        }

        Event fullTimeEvent = eventRepository.findByName("Full-Time");
        if(matchDetailRepository.countByMatchAndEventAndClubStat(match, fullTimeEvent, null) > 0)
        {
            MatchDetail matchDetailFullTime = matchDetailRepository.findByEvent(fullTimeEvent);
            if(matchDetailFullTime.getEventTime() < matchDetailDTO.getMinute())
                throw new Exception(String.format("This is event must be before full-time(%d')", matchDetailFullTime.getEventTime()));
        }

        PlayerStat playerStat = playerStatRepository.findById(matchDetailDTO.getPlayerStat()).get();
        ClubStat clubStat = clubStatRepository.findById(matchDetailDTO.getClubStat()).get();
        MatchDetail matchDetail = createNewEventMatch(playerStat, clubStat, event, match, matchDetailDTO.getMinute());
        if(event.getName().equals("Goal"))
        {
            ClubStat clubReceiveGoal = match.getAwayClubStat().equals(clubStat) ? match.getHomeClubStat() : match.getAwayClubStat();
            clubReceiveGoal.setGoalReceived(clubReceiveGoal.getGoalReceived() + 1);
            clubStatRepository.save(clubReceiveGoal);
        }
        if(matchDetailRepository.countByMatchAndEventAndClubStat(match, fullTimeEvent, null) > 0 && event.getName().equals("Goal"))
        {
            int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, fullTimeEvent, match.getHomeClubStat());
            int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, fullTimeEvent, match.getAwayClubStat());
            ClubStat homeClubStat = match.getHomeClubStat();
            ClubStat awayClubStat = match.getAwayClubStat();
            if(homeGoal == awayGoal)
            {
                if(clubStat.getId() == homeClubStat.getId())
                {
                    homeClubStat.setPoint(homeClubStat.getPoint() - 1 + 3);
                    homeClubStat.setDraw(homeClubStat.getDraw() - 1);
                    homeClubStat.setWin(homeClubStat.getWin() + 1);
                    clubStatRepository.save(homeClubStat);
                    awayClubStat.setPoint(awayClubStat.getPoint() - 1);
                    awayClubStat.setDraw(awayClubStat.getDraw() - 1);
                    awayClubStat.setLose(awayClubStat.getLose() + 1);
                    clubStatRepository.save(awayClubStat);
                }
                else
                {
                    awayClubStat.setPoint(awayClubStat.getPoint() + 3 - 1);
                    awayClubStat.setDraw(awayClubStat.getDraw() - 1);
                    awayClubStat.setWin(awayClubStat.getWin() + 1);
                    clubStatRepository.save(awayClubStat);
                    homeClubStat.setPoint(homeClubStat.getPoint() - 1);
                    homeClubStat.setDraw(homeClubStat.getDraw() - 1);
                    homeClubStat.setLose(homeClubStat.getLose() + 1);
                    clubStatRepository.save(homeClubStat);
                }
            }
            else if(homeGoal - awayGoal == 1)
            {
                if(clubStat.getId() == awayClubStat.getId())
                {
                    homeClubStat.setPoint(homeClubStat.getPoint() - 3 + 1);
                    homeClubStat.setWin(homeClubStat.getWin() - 1);
                    homeClubStat.setDraw(homeClubStat.getDraw() + 1);
                    clubStatRepository.save(homeClubStat);
                    awayClubStat.setPoint(awayClubStat.getPoint() + 1);
                    awayClubStat.setLose(awayClubStat.getLose() - 1);
                    awayClubStat.setDraw(awayClubStat.getDraw() + 1);
                    clubStatRepository.save(awayClubStat);
                }
            }
            else if(awayGoal - homeGoal == 1)
            {
                if(clubStat.getId() == homeClubStat.getId())
                {
                    awayClubStat.setPoint(awayClubStat.getPoint() -3 + 1);
                    awayClubStat.setWin(awayClubStat.getWin() - 1);
                    awayClubStat.setDraw(awayClubStat.getDraw() + 1);
                    clubStatRepository.save(awayClubStat);
                    homeClubStat.setPoint(homeClubStat.getPoint() + 1);
                    homeClubStat.setLose(homeClubStat.getLose() - 1);
                    homeClubStat.setDraw(homeClubStat.getDraw() + 1);
                    clubStatRepository.save(homeClubStat);
                }
            }
            updateTable(match.getSeason());
        }
        return matchDetailRepository.save(matchDetail);
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

    public void updateTable(Season season){
        List<ClubStat> clubStats = clubStatRepository.findBySeason(season);
        Collections.sort(clubStats, new Comparator<ClubStat>() {
            @Override
            public int compare(ClubStat o1, ClubStat o2) {
                if(o1.getPoint() == o2.getPoint())
                {
                    if(o1.getGoalTaken() - o1.getGoalReceived() == o2.getGoalTaken() - o2.getGoalReceived())
                    {
                        if(o1.getRedCard() == o2.getRedCard())
                        {
                            if(o1.getYellowCard() == o2.getYellowCard())
                            {
                                return o1.getFoul() - o2.getFoul();
                            }
                            return o1.getYellowCard() - o2.getYellowCard();
                        }
                        return o1.getRedCard() - o2.getRedCard();
                    }
                    return (o2.getGoalTaken() - o2.getGoalReceived()) - (o1.getGoalTaken() - o1.getGoalReceived());    
                }
                return o2.getPoint() - o1.getPoint();
            } 
        });
        for(int i = 0; i < clubStats.size(); i++)
        {
            clubStats.get(i).setRank(i + 1);
            clubStatRepository.save(clubStats.get(i));
        }
    }

    @Override
    public void deleteByMatchDetail(Long matchDetailId) throws Exception {
        MatchDetail matchDetail = matchDetailRepository.findById(matchDetailId).get();
        Match match = matchDetail.getMatch();
        Event fullTimEvent = eventRepository.findByName("Full-Time");
        Event goalEvent = eventRepository.findByName("Goal");
        if(matchDetail.getEvent().equals(goalEvent))
        {
            ClubStat clubReceiveGoal = match.getAwayClubStat().equals(matchDetail.getClubStat()) ? match.getHomeClubStat() : match.getAwayClubStat();
            clubReceiveGoal.setGoalReceived(clubReceiveGoal.getGoalReceived() - 1);
            clubStatRepository.save(clubReceiveGoal);
        }
        if(matchDetail.getEvent().equals(goalEvent) && matchDetailRepository.countByMatchAndEventAndClubStat(match, fullTimEvent, null) > 0)
        {
            ClubStat homeClubStat = match.getHomeClubStat();
            ClubStat awayClubStat = match.getAwayClubStat();
            int homeGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, homeClubStat);
            int awayGoal = matchDetailRepository.countByMatchAndEventAndClubStat(match, goalEvent, awayClubStat);
            if(homeGoal == awayGoal)
            {
                if(matchDetail.getClubStat().equals(homeClubStat))
                {
                    homeClubStat.setDraw(homeClubStat.getDraw() - 1);
                    homeClubStat.setLose(homeClubStat.getLose() + 1);
                    homeClubStat.setPoint(homeClubStat.getPoint() - 1);
                    awayClubStat.setDraw(awayClubStat.getDraw() - 1);
                    awayClubStat.setWin(awayClubStat.getWin() + 1);
                    awayClubStat.setPoint(awayClubStat.getPoint() -1 + 3);
                }
                else
                {
                    awayClubStat.setDraw(awayClubStat.getDraw() - 1);
                    awayClubStat.setLose(awayClubStat.getLose() + 1);
                    awayClubStat.setPoint(awayClubStat.getPoint() - 1);
                    homeClubStat.setDraw(homeClubStat.getDraw() - 1);
                    homeClubStat.setWin(homeClubStat.getWin() + 1);
                    homeClubStat.setPoint(homeClubStat.getPoint() - 1 + 3);
                }
            }
            else if(homeGoal - awayGoal == 1)
            {
                if(matchDetail.getClubStat().equals(homeClubStat))
                {
                    awayClubStat.setDraw(awayClubStat.getDraw() + 1);
                    awayClubStat.setLose(awayClubStat.getLose() - 1);
                    awayClubStat.setPoint(awayClubStat.getPoint() + 1);
                    homeClubStat.setDraw(homeClubStat.getDraw() + 1);
                    homeClubStat.setWin(homeClubStat.getWin() - 1);
                    homeClubStat.setPoint(homeClubStat.getPoint() + 1 - 3);
                }
            }
            else if(awayGoal - homeGoal == 1)
            {
                if(matchDetail.getClubStat().equals(awayClubStat))
                {
                    homeClubStat.setDraw(homeClubStat.getDraw() + 1);
                    homeClubStat.setLose(homeClubStat.getLose() - 1);
                    homeClubStat.setPoint(homeClubStat.getPoint() + 1);
                    awayClubStat.setDraw(awayClubStat.getDraw() + 1);
                    awayClubStat.setWin(awayClubStat.getWin() - 1);
                    awayClubStat.setPoint(awayClubStat.getPoint() + 1 - 3);
                }
            }
            updateTable(matchDetail.getMatch().getSeason());
        }
        matchDetailRepository.delete(matchDetail);
        return;
    }

    @Override
    public MatchDetail getMatchDetailById(Long matchDetailId) {
        return matchDetailRepository.findById(matchDetailId).get();
    }
}
