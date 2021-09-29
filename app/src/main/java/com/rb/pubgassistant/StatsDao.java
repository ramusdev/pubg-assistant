package com.rb.pubgassistant;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public abstract class StatsDao {
    @Insert
    abstract long insert(StatsEntity statsEntity);
}
