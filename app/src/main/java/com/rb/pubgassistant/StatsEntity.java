package com.rb.pubgassistant;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "stats",
        foreignKeys = @ForeignKey(entity = PlayerEntity.class, parentColumns = "id", childColumns = "playerId", onDelete = CASCADE)
)
public class StatsEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public int assists;
    public int boosts;
    public int dbnos;
    public int dailyKills;
    public int dailyWins;
    public double damageDealt;
    public int days;
    public int headshotKills;
    public int heals;
    public int killPoints;
    public int kills;
    public double longestKill;
    public int longestTimeSurvived;
    public int losses;
    public int maxKillStreaks;
    public int mostSurvivalTime;
    public int rankPoints;
    public String rankPointsTitle;
    public int revives;
    public double rideDistance;
    public int roadKills;
    public int roundMostKills;
    public int roundsPlayed;
    public int suicides;
    public int swimDistance;
    public int teamKills;
    public int timeSurvived;
    public int top10s;
    public int vehicleDestroys;
    public double walkDistance;
    public int weaponsAcquired;
    public int weeklyKills;
    public int weeklyWins;
    public int winPoints;
    public int wins;
    public String mode;
    public long playerId;
    public String seasonId;

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getBoosts() {
        return boosts;
    }

    public void setBoosts(int boosts) {
        this.boosts = boosts;
    }

    public int getDbnos() {
        return dbnos;
    }

    public void setDbnos(int dbnos) {
        this.dbnos = dbnos;
    }

    public int getDailyKills() {
        return dailyKills;
    }

    public void setDailyKills(int dailyKills) {
        this.dailyKills = dailyKills;
    }

    public int getDailyWins() {
        return dailyWins;
    }

    public void setDailyWins(int dailyWins) {
        this.dailyWins = dailyWins;
    }

    public double getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(double damageDealt) {
        this.damageDealt = damageDealt;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(int headshotKills) {
        this.headshotKills = headshotKills;
    }

    public int getHeals() {
        return heals;
    }

    public void setHeals(int heals) {
        this.heals = heals;
    }

    public int getKillPoints() {
        return killPoints;
    }

    public void setKillPoints(int killPoints) {
        this.killPoints = killPoints;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public double getLongestKill() {
        return longestKill;
    }

    public void setLongestKill(double longestKill) {
        this.longestKill = longestKill;
    }

    public int getLongestTimeSurvived() {
        return longestTimeSurvived;
    }

    public void setLongestTimeSurvived(int longestTimeSurvived) {
        this.longestTimeSurvived = longestTimeSurvived;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getMaxKillStreaks() {
        return maxKillStreaks;
    }

    public void setMaxKillStreaks(int maxKillStreaks) {
        this.maxKillStreaks = maxKillStreaks;
    }

    public int getMostSurvivalTime() {
        return mostSurvivalTime;
    }

    public void setMostSurvivalTime(int mostSurvivalTime) {
        this.mostSurvivalTime = mostSurvivalTime;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

    public String getRankPointsTitle() {
        return rankPointsTitle;
    }

    public void setRankPointsTitle(String rankPointsTitle) {
        this.rankPointsTitle = rankPointsTitle;
    }

    public int getRevives() {
        return revives;
    }

    public void setRevives(int revives) {
        this.revives = revives;
    }

    public double getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(double rideDistance) {
        this.rideDistance = rideDistance;
    }

    public int getRoadKills() {
        return roadKills;
    }

    public void setRoadKills(int roadKills) {
        this.roadKills = roadKills;
    }

    public int getRoundMostKills() {
        return roundMostKills;
    }

    public void setRoundMostKills(int roundMostKills) {
        this.roundMostKills = roundMostKills;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public int getSuicides() {
        return suicides;
    }

    public void setSuicides(int suicides) {
        this.suicides = suicides;
    }

    public int getSwimDistance() {
        return swimDistance;
    }

    public void setSwimDistance(int swimDistance) {
        this.swimDistance = swimDistance;
    }

    public int getTeamKills() {
        return teamKills;
    }

    public void setTeamKills(int teamKills) {
        this.teamKills = teamKills;
    }

    public int getTimeSurvived() {
        return timeSurvived;
    }

    public void setTimeSurvived(int timeSurvived) {
        this.timeSurvived = timeSurvived;
    }

    public int getTop10s() {
        return top10s;
    }

    public void setTop10s(int top10s) {
        this.top10s = top10s;
    }

    public int getVehicleDestroys() {
        return vehicleDestroys;
    }

    public void setVehicleDestroys(int vehicleDestroys) {
        this.vehicleDestroys = vehicleDestroys;
    }

    public double getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(double walkDistance) {
        this.walkDistance = walkDistance;
    }

    public int getWeaponsAcquired() {
        return weaponsAcquired;
    }

    public void setWeaponsAcquired(int weaponsAcquired) {
        this.weaponsAcquired = weaponsAcquired;
    }

    public int getWeeklyKills() {
        return weeklyKills;
    }

    public void setWeeklyKills(int weeklyKills) {
        this.weeklyKills = weeklyKills;
    }

    public int getWeeklyWins() {
        return weeklyWins;
    }

    public void setWeeklyWins(int weeklyWins) {
        this.weeklyWins = weeklyWins;
    }

    public int getWinPoints() {
        return winPoints;
    }

    public void setWinPoints(int winPoints) {
        this.winPoints = winPoints;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return String.valueOf(assists);
    }


}
