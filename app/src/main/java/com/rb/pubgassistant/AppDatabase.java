package com.rb.pubgassistant;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlayerEntity.class, StatsEntity.class}, version = 24)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
    public abstract StatsDao statsDao();
}
