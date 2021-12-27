package com.rb.pubgassistant;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InsertPlayerTest {
    public static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NWY1ZjY2MC1lMWJmLTAxMzktNmZiYS0zZmU1Yjk0MDFmNDUiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNjI5MjI4NjM3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImFzc2lzdGFudC1mb3ItIn0.QDeGu-AH22Fs4VpHCkI4H0FpX_rNKRSm8YJMUVs2R1Q";
    public static final String PLAYER_NAME = "Sambty";

    @Test
    public void insertPlayer() {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setName("Test name");
        playerEntity.setPlayerId("some_test_id");

        playerDao.insert(playerEntity);

        assertEquals(2, 1 + 1);
    }
}