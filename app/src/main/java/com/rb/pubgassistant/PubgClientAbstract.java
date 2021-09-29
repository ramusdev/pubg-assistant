package com.rb.pubgassistant;

import android.util.Log;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class PubgClientAbstract {
    public static final String ACCEPT_HEADER = "application/vnd.api+json";
    public static final String USER_AGENT_HEADER = "Googlebot";
    public CloseableHttpClient closeableHttpClient;
    public String apiKey;
    public URIBuilder uriBuilder;

    protected PubgClientAbstract(String apiKey) {

        this.apiKey = apiKey;

        closeableHttpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();
    }

    protected Map<String, String> makeGetCall(String url, ArrayList<NameValuePair> parameters) {
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        uriBuilder.addParameters(parameters);

        return makeGet();
    }

    protected Map<String, String> makeGetCall(String url) {

        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        return makeGet();
    }

    private Map<String, String> makeGet() {
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        httpGet.addHeader(HttpHeaders.USER_AGENT, USER_AGENT_HEADER);
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, apiKey);
        httpGet.addHeader(HttpHeaders.ACCEPT, ACCEPT_HEADER);

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        HttpEntity httpEntity = closeableHttpResponse.getEntity();

        String responseString = null;
        try {
            responseString = EntityUtils.toString(httpEntity);
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }

        // Log.d("MyTag", "Inside abstract client");
        // Log.d("MyTag", String.valueOf(closeableHttpResponse.getCode()));

        Map<String, String> response = new HashMap<>();
        response.put("code", String.valueOf(closeableHttpResponse.getCode()));
        response.put("response", responseString);

        return response;
    }

    public abstract PlayerEntity getPlayerId(String playerName);
    public abstract ArrayList<StatsEntity> getPlayerInfo(String playerId, String seasonId);
}
