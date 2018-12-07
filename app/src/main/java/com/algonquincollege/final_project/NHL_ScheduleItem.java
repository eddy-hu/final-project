package com.algonquincollege.final_project;


import java.util.Date;

public class NHL_ScheduleItem {

    public String homeCityD;
    public String awayCityD;
    public String homeTeamD;
    public String awayTeamD;
    public String playedStatusD;
    public String homeScoreD;
    public String awayScoreD;
    public String dateTimeD;
    public int homeLogoD;
    public int awayLogoD;


    public NHL_ScheduleItem() {

    }

    public NHL_ScheduleItem(String homeCityD,String awayCityD,String homeTeamD, String awayTeamD, String playedStatusD, String homeScoreD, String awayScoreD, String dateTimeD,int homeLogoD,int awayLogoD) {
        this.homeCityD = homeCityD;
        this.awayCityD = awayCityD;
        this.homeTeamD = homeTeamD;
        this.awayTeamD = awayTeamD;
        this.playedStatusD = playedStatusD;
        this.homeScoreD = homeScoreD;
        this.awayScoreD = awayScoreD;
        this.dateTimeD = dateTimeD;
        this.homeLogoD = homeLogoD;
        this.awayLogoD = awayLogoD;


    }
    public String getHomeCity() { return homeCityD; }
    public void setHomeCity(String homeCityD) { this.homeCityD = homeCityD; }
    public String getAwayCity() { return awayCityD; }
    public void setAwayCity(String awayCityD) { this.awayCityD = awayCityD; }
    public String getHomeTeam() {
        return homeTeamD;
    }
    public void setHomeTeam(String homeTeamD) {
        this.homeTeamD = homeTeamD;
    }
    public String getAwayTeam() {
        return awayTeamD;
    }
    public void setAwayTeam(String awayTeamD) {
        this.awayTeamD = awayTeamD;
    }
    public String getPlayedStatus() {
        return playedStatusD;
    }
    public void setPlayedStatus(String playedStatusD) {
        this.playedStatusD = playedStatusD;
    }
    public String getHomeScore() {
        return homeScoreD;
    }
    public void setHomeScore(String homeScoreD) {
        this.homeScoreD = homeScoreD;
    }
    public String getAwayScore() {
        return awayScoreD;
    }
    public void setAwayScore(String awayScoreD) {
        this.awayScoreD = awayScoreD;
    }
    public String getDateTime() {
        return dateTimeD;
    }
    public void setDateTime(String dateTimeD) {
        this.dateTimeD = dateTimeD;
    }
    public int getHomeLogo() { return homeLogoD; }
    public void setHomeLogo(int homeLogoD) { this.homeLogoD = homeLogoD; }
    public int getAwayLogo() { return awayLogoD; }
    public void setAwayLogo(int awayLogoD) { this.awayLogoD = awayLogoD; }

}
