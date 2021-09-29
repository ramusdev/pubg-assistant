package com.rb.pubgassistant;

import android.util.Log;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class PubgClient extends PubgClientAbstract {

    protected PubgClient(String apiKey) {
        super(apiKey);
    }

    @Override
    public PlayerEntity getPlayerId(String playerName) {

        ArrayList<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("filter[playerNames]", playerName));

        String url = PubgEndpointsEnum.PLAYERS.getEndpoint();
        Map<String, String> response = super.makeGetCall(url, parameters);

        if (Integer.parseInt(response.get("code")) == 404) {
            return null;
        }

        PlayerEntity playerEntity = null;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(PlayerEntity.class, new PubgDeserializer.PlayerDeserializer());

            Gson gson = gsonBuilder.create();
            playerEntity = gson.fromJson(response.get("response"), PlayerEntity.class);
            playerEntity.setResponseCode(response.get("code"));
        } catch (Exception e) {
            Log.d("MyTag", "Error json syntax exception");
            Log.d("MyTag", e.getMessage());
        }

        return playerEntity;
    }

    public ArrayList<StatsEntity> getPlayerInfo(String playerId, String seasonId) {

        String url = PubgEndpointsEnum.PLAYERS.getEndpoint() + String.format("/%s/seasons/%s", playerId, seasonId);
        Map<String, String> response = super.makeGetCall(url);

        Type typeToken = new TypeToken<ArrayList<StatsEntity>>() {}.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(typeToken, new PubgDeserializer.PubgPlayerModelDeserializer());

        Gson gson = gsonBuilder.create();
        ArrayList<StatsEntity> statsEntities = gson.fromJson(response.get("response"), typeToken);

        return statsEntities;
    }


}
