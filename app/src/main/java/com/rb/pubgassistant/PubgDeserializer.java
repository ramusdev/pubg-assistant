package com.rb.pubgassistant;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PubgDeserializer {

    public static class PubgPlayerModelDeserializer implements JsonDeserializer<ArrayList<StatsEntity>> {

        @Override
        public ArrayList<StatsEntity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement jsonElementData = json.getAsJsonObject().get("data");
            JsonElement jsonElementAttributes = jsonElementData.getAsJsonObject().get("attributes");
            JsonElement jsonElementGameModeStats = jsonElementAttributes.getAsJsonObject().get("gameModeStats");
            String[] gameMode = {"duo", "duo-fpp", "solo", "solo-fpp", "squad", "squad-fpp"};

            JsonElement jsonElementRelationships = jsonElementData.getAsJsonObject().get("relationships");
            JsonElement jsonElementSeason = jsonElementRelationships.getAsJsonObject().get("season");
            JsonElement jsonElementSeasonData = jsonElementSeason.getAsJsonObject().get("data");
            String seasonId = jsonElementSeasonData.getAsJsonObject().get("id").getAsString();

            ArrayList<StatsEntity> statsEntityList = new ArrayList<>();
            for (String mode : gameMode) {
                StatsEntity statsEntity = new StatsEntity();
                JsonElement modeElement = jsonElementGameModeStats.getAsJsonObject().get(mode);

                statsEntity.setAssists(modeElement.getAsJsonObject().get("assists").getAsInt());
                statsEntity.setBoosts(modeElement.getAsJsonObject().get("boosts").getAsInt());
                statsEntity.setDbnos(modeElement.getAsJsonObject().get("dBNOs").getAsInt());
                statsEntity.setDailyKills(modeElement.getAsJsonObject().get("dailyKills").getAsInt());
                statsEntity.setDailyWins(modeElement.getAsJsonObject().get("dailyWins").getAsInt());
                statsEntity.setDamageDealt(modeElement.getAsJsonObject().get("damageDealt").getAsDouble());
                statsEntity.setDays(modeElement.getAsJsonObject().get("days").getAsInt());
                statsEntity.setHeadshotKills(modeElement.getAsJsonObject().get("headshotKills").getAsInt());
                statsEntity.setHeals(modeElement.getAsJsonObject().get("heals").getAsInt());
                statsEntity.setKillPoints(modeElement.getAsJsonObject().get("killPoints").getAsInt());
                statsEntity.setKills(modeElement.getAsJsonObject().get("kills").getAsInt());
                statsEntity.setLongestKill(modeElement.getAsJsonObject().get("longestKill").getAsDouble());
                statsEntity.setLongestTimeSurvived(modeElement.getAsJsonObject().get("longestTimeSurvived").getAsInt());
                statsEntity.setLosses(modeElement.getAsJsonObject().get("losses").getAsInt());
                statsEntity.setMaxKillStreaks(modeElement.getAsJsonObject().get("maxKillStreaks").getAsInt());
                statsEntity.setMostSurvivalTime(modeElement.getAsJsonObject().get("mostSurvivalTime").getAsInt());
                statsEntity.setRankPoints(modeElement.getAsJsonObject().get("rankPoints").getAsInt());
                statsEntity.setRankPointsTitle(modeElement.getAsJsonObject().get("rankPointsTitle").getAsString());
                statsEntity.setRevives(modeElement.getAsJsonObject().get("revives").getAsInt());
                statsEntity.setRideDistance(modeElement.getAsJsonObject().get("rideDistance").getAsDouble());
                statsEntity.setRoadKills(modeElement.getAsJsonObject().get("roadKills").getAsInt());
                statsEntity.setRoundMostKills(modeElement.getAsJsonObject().get("roundMostKills").getAsInt());
                statsEntity.setRoundsPlayed(modeElement.getAsJsonObject().get("roundsPlayed").getAsInt());
                statsEntity.setSuicides(modeElement.getAsJsonObject().get("suicides").getAsInt());
                statsEntity.setSwimDistance(modeElement.getAsJsonObject().get("swimDistance").getAsInt());
                statsEntity.setTeamKills(modeElement.getAsJsonObject().get("teamKills").getAsInt());
                statsEntity.setTimeSurvived(modeElement.getAsJsonObject().get("timeSurvived").getAsInt());
                statsEntity.setTop10s(modeElement.getAsJsonObject().get("top10s").getAsInt());
                statsEntity.setVehicleDestroys(modeElement.getAsJsonObject().get("vehicleDestroys").getAsInt());
                statsEntity.setWalkDistance(modeElement.getAsJsonObject().get("walkDistance").getAsDouble());
                statsEntity.setWeaponsAcquired(modeElement.getAsJsonObject().get("weaponsAcquired").getAsInt());
                statsEntity.setWeeklyKills(modeElement.getAsJsonObject().get("weeklyKills").getAsInt());
                statsEntity.setWeeklyWins(modeElement.getAsJsonObject().get("weeklyWins").getAsInt());
                statsEntity.setWinPoints(modeElement.getAsJsonObject().get("winPoints").getAsInt());
                statsEntity.setWins(modeElement.getAsJsonObject().get("wins").getAsInt());
                statsEntity.setMode(mode);
                statsEntity.setSeasonId(seasonId);

                statsEntityList.add(statsEntity);
            }

            return statsEntityList;
        }
    }

    public static class PlayerDeserializer implements JsonDeserializer<PlayerEntity> {

        @Override
        public PlayerEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement jsonElementData = json.getAsJsonObject().get("data");
            JsonElement jsonElement = jsonElementData.getAsJsonArray().get(0);
            String playerId = jsonElement.getAsJsonObject().get("id").getAsString();
            JsonElement jsonElementAttributes = jsonElement.getAsJsonObject().get("attributes");
            String playerName = jsonElementAttributes.getAsJsonObject().get("name").getAsString();

            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setPlayerId(playerId);
            playerEntity.setName(playerName);

            return playerEntity;
        }
    }

}



