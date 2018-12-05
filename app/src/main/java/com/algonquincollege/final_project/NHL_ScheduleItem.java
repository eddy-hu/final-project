package com.algonquincollege.final_project;

import java.sql.Date;

public class NHL_ScheduleItem {

    public String homeTeam;
    public String awayTeam;
    public String playedStatus;
    public String homeScore;
    public String awayScore;
    public String dateTime;
    public int LogoId;

    public NHL_ScheduleItem() {

    }
    public NHL_ScheduleItem(String homeTeam, String awayTeam, String playedStatus, String homeScore, String awayScore, String dateTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.playedStatus = playedStatus;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.dateTime = dateTime;
    }



    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getPlayedStatus() {
        return playedStatus;
    }

    public void setPlayedStatus(String playedStatus) {
        this.playedStatus = playedStatus;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


}
