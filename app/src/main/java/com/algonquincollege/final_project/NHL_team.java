package com.algonquincollege.final_project;

public class NHL_team {
    int logoId;
    String city;
    String teamName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NHL_team nhl_team = (NHL_team) o;

        if (logoId != nhl_team.logoId) return false;
        if (!city.equals(nhl_team.city)) return false;
        return teamName.equals(nhl_team.teamName);
    }

    @Override
    public int hashCode() {
        int result = logoId;
        result = 31 * result + city.hashCode();
        result = 31 * result + teamName.hashCode();
        return result;
    }
}

