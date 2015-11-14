package com.kennanseno.ultimate_scoreboard_app.Model;

public class Schedule {

    private int matchId;
    private String club1Id;
    private int club1Score;
    private int club1SpiritScore;
    private String club2Id;
    private int club2Score;
    private int club2SpiritScore;
    private String startTime;
    private String endTime;
    private int day;
    private int eventId;

    public Schedule(){

    }

    public Schedule(int matchId, String club1Id, int club1Score, int club1SpiritScore, String club2Id, int club2Score, int club2SpiritScore, String startTime, String endTime, int day, int eventId) {
        this.matchId = matchId;
        this.club1Id = club1Id;
        this.club1Score = club1Score;
        this.club1SpiritScore = club1SpiritScore;
        this.club2Id = club2Id;
        this.club2Score = club2Score;
        this.club2SpiritScore = club2SpiritScore;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.eventId = eventId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getClub1Id() {
        return club1Id;
    }

    public void setClub1Id(String club1Id) {
        this.club1Id = club1Id;
    }

    public int getClub1Score() {
        return club1Score;
    }

    public void setClub1Score(int club1Score) {
        this.club1Score = club1Score;
    }

    public String getClub2Id() {
        return club2Id;
    }

    public void setClub2Id(String club2Id) {
        this.club2Id = club2Id;
    }

    public int getClub2Score() {
        return club2Score;
    }

    public void setClub2Score(int club2Score) {
        this.club2Score = club2Score;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getClub2SpiritScore() {
        return club2SpiritScore;
    }

    public void setClub2SpiritScore(int club2SpiritScore) {
        this.club2SpiritScore = club2SpiritScore;
    }

    public int getClub1SpiritScore() {
        return club1SpiritScore;
    }

    public void setClub1SpiritScore(int club1SpiritScore) {
        this.club1SpiritScore = club1SpiritScore;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
