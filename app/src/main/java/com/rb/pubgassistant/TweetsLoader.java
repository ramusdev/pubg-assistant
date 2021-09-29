package com.rb.pubgassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rb.pubgassistant.data.DataContract;
import com.rb.pubgassistant.data.DataDbHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TweetsLoader {

    private Context context;
    private List<Tweet> tweetArray;

    public TweetsLoader(Context context) {
        this.context = context;
        tweetArray = new ArrayList<Tweet>();
    }

    public List<Tweet> load() {

        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        // String[] selectionArgs = { "Title 10" };
        String orderBy = DataContract.TweetsEntry.COLUMN_DATE + " DESC";
        String limit = "15";
        Cursor cursor = sqLiteDatabase.query(DataContract.TweetsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                orderBy,
                limit
        );

        while (cursor.moveToNext()) {
            Tweet tweet = new Tweet();

            tweet.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TweetsEntry.COLUMN_DATE)));
            tweet.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TweetsEntry.COLUMN_TEXT)));
            tweet.setId(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TweetsEntry.COLUMN_TWEETID)));

            Gson gson = new Gson();

            // Type tt = new TypeToken<ArrayList>(){}.getType();
            String stringArray = cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TweetsEntry.COLUMN_IMAGE));
            ArrayList<String> images = gson.fromJson(stringArray, new TypeToken<ArrayList<String>>(){}.getType());
            tweet.setImages(images);

            tweetArray.add(tweet);
        }

        cursor.close();

        return tweetArray;
    }

    public TweetsLoader loadThen() {
        load();
        return this;
    }

    /*
    public static List<News> convertDateToView(List<News> newsArray) {
        for (News news : newsArray) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH);
            LocalDateTime dateTime = LocalDateTime.parse(news.getDate(), formatter);

            Locale locale = new Locale(Locale.getDefault().getLanguage());
            DateTimeFormatter formatterTo = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
            String dateTo = dateTime.format(formatterTo);
            news.setDate(dateTo);
        }

        return newsArray;
    }
    */

    public List<Tweet> convertDateToView() {
        for (Tweet tweet : tweetArray) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'", Locale.ENGLISH);
            LocalDateTime dateTime = LocalDateTime.parse(tweet.getDate(), formatter);

            Locale locale = new Locale(Locale.getDefault().getLanguage());
            DateTimeFormatter formatterTo = DateTimeFormatter.ofPattern("dd MMMM", locale);
            String dateTo = dateTime.format(formatterTo);
            tweet.setDate(dateTo);
        }

        return tweetArray;
    }
}
