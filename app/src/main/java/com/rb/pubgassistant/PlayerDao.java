package com.rb.pubgassistant;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class PlayerDao {
    @Query("SELECT * FROM player")
    abstract List<PlayerEntity> getAll();

    @Query("SELECT * FROM player WHERE id = :id")
    abstract PlayerEntity getById(long id);

    @Query("SELECT * FROM player WHERE name = :name")
    abstract PlayerEntity getByName(String name);

    @Insert
    abstract long insert(PlayerEntity playerEntity);

    @Insert
    abstract void insertAll(List<StatsEntity> statsEntities);

    @Update
    abstract void updateAll(List<StatsEntity> statsEntities);

    @Update
    abstract void update(StatsEntity statsEntity);

    @Query("SELECT * FROM player WHERE player.name LIKE :name")
    abstract PlayerStats getPlayerWithStatsByName(String name);

    @Delete
    abstract void delete(PlayerEntity playerEntity);

    @Query("SELECT player.id FROM player LIMIT 1")
    abstract Long getTopOnePlayerId();

    /*
    @Query("SELECT * " +
            "FROM player " +
            "INNER JOIN stats ON stats.playerId = player.playerId " +
            "WHERE player.name LIKE :name " +
            "AND stats.seasonId LIKE :seasonId"
    )
    abstract List<PlayerStats> getPlayer(String name, String seasonId);
    */

    @Query("SELECT * FROM player")
    abstract List<PlayerEntity> getPlayers();

    @Query("SELECT * FROM stats WHERE stats.playerId = :playerId AND stats.seasonId LIKE :seasonId")
    abstract List<StatsEntity> getStatsByPlayerAndSeason(long playerId, String seasonId);

    @Query("SELECT * FROM stats WHERE stats.playerId = :playerId")
    abstract List<StatsEntity> getStatsByPlayer(long playerId);

    @Query("SELECT * FROM stats WHERE stats.playerId = :playerId AND stats.seasonId = :season AND stats.mode = :mode")
    abstract StatsEntity getStatsEntityByPlayerSeasonMode(long playerId, String season, String mode);

    public void updatePlayerStats(PlayerEntity playerEntity, List<StatsEntity> statsEntities) {
        for (StatsEntity statsEntity : statsEntities) {
            long playerId = playerEntity.getId();
            String season = statsEntity.getSeasonId();
            String mode = statsEntity.getMode();

            StatsEntity statsDb = this.getStatsEntityByPlayerSeasonMode(playerId, season, mode);
            statsEntity.setId(statsDb.getId());
            statsEntity.setPlayerId(statsDb.getPlayerId());

            this.update(statsEntity);
        }

    }

    public PlayerStats getPlayerStatsByNameSeasons(String name, String seasonId) {
        PlayerEntity playerEntity = this.getByName(name);
        List<StatsEntity> statsEntity = getStatsByPlayerAndSeason(playerEntity.getId(), seasonId);

        PlayerStats playerStats = new PlayerStats();
        playerStats.setName(playerEntity.getName());
        playerStats.setId(playerEntity.getId());
        playerStats.setStats(statsEntity);

        return playerStats;
    }

    public PlayerStats getPlayerStatsByIdSeasons(long playerId, String seasonId) {
        PlayerEntity playerEntity = this.getById(playerId);
        List<StatsEntity> statsEntity = getStatsByPlayerAndSeason(playerEntity.getId(), seasonId);

        PlayerStats playerStats = new PlayerStats();
        playerStats.setName(playerEntity.getName());
        playerStats.setId(playerEntity.getId());
        playerStats.setStats(statsEntity);

        return playerStats;
    }

    public void insertPlayerWithStats(PlayerEntity playerEntity, List<StatsEntity> statsEntities) {
        long playerId = this.insert(playerEntity);

        for (StatsEntity statsEntity : statsEntities) {
            statsEntity.setPlayerId(playerId);
        }

        insertAll(statsEntities);
    }
}