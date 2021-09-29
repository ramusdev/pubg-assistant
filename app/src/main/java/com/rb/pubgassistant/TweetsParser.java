package com.rb.pubgassistant;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

public class TweetsParser {

    private String bearerToken;
    public String tweetResponse;

    public TweetsParser(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public ArrayList<Tweet> getTweets(String userId) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        Map<String, String> media = new HashMap<String, String>();

        fetchTweets(userId);
        tweets = parseData();
        media = parseImages();
        addImageUrlToTweets(tweets, media);

        return tweets;
    }

    public void addImageUrlToTweets(ArrayList<Tweet> tweets, Map<String, String> media) {
        tweets.forEach(t -> {
            ArrayList<String> imageKeys = t.getImageKeys();
            imageKeys.forEach(i -> {
                if (media.containsKey(i)) {
                    t.setImage(media.get(i));
                }
            });
        });
    }

    public void fetchTweets(String userId) {
        URIBuilder uriBuilder = null;
        HttpGet httpGet = null;

        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();

        try {
            uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        queryParameters.add(new BasicNameValuePair("expansions", "attachments.media_keys"));
        queryParameters.add(new BasicNameValuePair("media.fields", "duration_ms,height,media_key,preview_image_url,public_metrics,type,url,width"));

        uriBuilder.addParameters(queryParameters);

        try {
            httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
            httpGet.setHeader("Content-Type", "application/json");

        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        try {
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            if (null != entity) {
                tweetResponse = EntityUtils.toString(entity);
            }
        } catch (IOException | ParseException e ) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Tweet> parseData() {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(tweetResponse).getAsJsonObject();
        JsonElement dataArray = jsonObject.get("data");
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for (JsonElement item : dataArray.getAsJsonArray()) {
            Gson gson = new Gson();
            Tweet tweet = gson.fromJson(item, Tweet.class);

            JsonElement attachments = item.getAsJsonObject().get("attachments");
            if (attachments != null) {
                JsonElement mediaKeys = attachments.getAsJsonObject().get("media_keys");
                for (JsonElement mediaItem : mediaKeys.getAsJsonArray()) {
                    tweet.setImageKeys(mediaItem.getAsString());
                }
            }

            tweets.add(tweet);
        }

        return tweets;
    }

    public Map<String, String> parseImages() {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(tweetResponse).getAsJsonObject();
        JsonObject includes = jsonObject.get("includes").getAsJsonObject();
        Map<String, String> media = new HashMap<String, String>();

        for (JsonElement item : includes.get("media").getAsJsonArray()) {
            String url = null;
            String type = item.getAsJsonObject().get("type").getAsString();
            String key = item.getAsJsonObject().get("media_key").getAsString();

            if (type.equals("photo")) {
                url = item.getAsJsonObject().get("url").getAsString();
            } else if (type.equals("video") || type.equals("GIF") || type.equals("animated_gif")) {
                url = item.getAsJsonObject().get("preview_image_url").getAsString();
            }

            media.put(key, url);
        }

        return media;
    }

}

