package com.rb.pubgassistant;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PlayerStatsCallable implements Callable<Integer> {

    public static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NWY1ZjY2MC1lMWJmLTAxMzktNmZiYS0zZmU1Yjk0MDFmNDUiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNjI5MjI4NjM3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImFzc2lzdGFudC1mb3ItIn0.QDeGu-AH22Fs4VpHCkI4H0FpX_rNKRSm8YJMUVs2R1Q";
    public String name;

    public PlayerStatsCallable(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        // Log.d("MyTag", "Inside stats");

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();
        StatsDao statsDao = appDatabase.statsDao();

        PubgClient pubgClient = new PubgClient(API_KEY);
        PlayerEntity playerEntity = pubgClient.getPlayerId(name);

        // Log.d("MyTag", "After stats");
        // Log.d("MyTag", playerEntity.getResponseCode());

        if (playerEntity == null) {
            return 404;
        }

        ArrayList<StatsEntity> statsEntitiesOne = pubgClient.getPlayerInfo(playerEntity.getPlayerId(), PubgSeasonsEnum.PC_2018_11.getSeasonId());
        ArrayList<StatsEntity> statsEntitiesTwo = pubgClient.getPlayerInfo(playerEntity.getPlayerId(), PubgSeasonsEnum.PC_2018_12.getSeasonId());
        ArrayList<StatsEntity> statsEntitiesThree = pubgClient.getPlayerInfo(playerEntity.getPlayerId(), PubgSeasonsEnum.PC_2018_13.getSeasonId());

        statsEntitiesOne.addAll(statsEntitiesTwo);
        statsEntitiesOne.addAll(statsEntitiesThree);

        playerDao.insertPlayerWithStats(playerEntity, statsEntitiesOne);

        return 200;
    }
}
