package com.rb.pubgassistant;

import java.util.List;
import androidx.room.Relation;

public class PlayerStats {
    public long id;
    public String name;
    public String date;
    public String playerId;

    @Relation(parentColumn = "id", entityColumn = "playerId")
    public List<StatsEntity> stats;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public List<StatsEntity> getStats() {
        return stats;
    }

    public void setStats(List<StatsEntity> stats) {
        this.stats = stats;
    }
}
