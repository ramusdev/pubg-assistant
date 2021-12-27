package com.rb.pubgassistant;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.navigation.Navigator;

import org.junit.Test;

public class PubgClientTest {
    public static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NWY1ZjY2MC1lMWJmLTAxMzktNmZiYS0zZmU1Yjk0MDFmNDUiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNjI5MjI4NjM3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImFzc2lzdGFudC1mb3ItIn0.QDeGu-AH22Fs4VpHCkI4H0FpX_rNKRSm8YJMUVs2R1Q";
    public static final String PLAYER_NAME = "Sambty";

    @Test
    public void pubgClient() {
        PubgClient pubgClient = new PubgClient(API_KEY);
        PlayerEntity playerEntity = pubgClient.getPlayerId(PLAYER_NAME);
        String playerId = playerEntity.getPlayerId();

        assertEquals("account.4ac950e7109b480e9a124d3b4d75f9cc1", playerId);
    }

    @Test
    public void pubgSpeed() {
        assertEquals(1, 1);
    }
}