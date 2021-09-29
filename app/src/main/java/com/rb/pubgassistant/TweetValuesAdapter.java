package com.rb.pubgassistant;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.rb.pubgassistant.data.DataContract;

public class TweetValuesAdapter implements ValuesAdapterInterface {

    public static ContentValues convert(Tweet tweet) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.TweetsEntry.COLUMN_TWEETID, tweet.getId());
        contentValues.put(DataContract.TweetsEntry.COLUMN_DATE, tweet.getDate());

        Gson gson = new Gson();
        String stringImages = gson.toJson(tweet.getImages());

        contentValues.put(DataContract.TweetsEntry.COLUMN_IMAGE, stringImages);
        contentValues.put(DataContract.TweetsEntry.COLUMN_TEXT, tweet.getText());
        contentValues.put(DataContract.TweetsEntry.COLUMN_NOTIFIED, 0);

        return contentValues;
    }
}
