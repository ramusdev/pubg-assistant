package com.rb.pubgassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.rb.pubgassistant.data.DataContract;
import com.rb.pubgassistant.data.DataDbHelper;

public class NewsLoader {

    private Context context;
    private List<News> newsArray;

    public NewsLoader(Context context) {
        this.context = context;
        newsArray = new ArrayList<News>();
    }

    public List<News> load() {

        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { "Title 10" };
        String orderBy = DataContract.NewsEntry.COLUMN_DATE + " DESC";
        String limit = "15";
        Cursor cursor = sqLiteDatabase.query(DataContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                orderBy,
                limit
        );

        while (cursor.moveToNext()) {
            News news = new News();

            news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_TITLE)));
            news.setPreviewText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_PREVIEWTEXT)));
            news.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_TEXT)));
            news.setLink(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_LINK)));
            news.setImage(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_IMAGE)));
            news.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_DATE)));

            // Log.e("CustomLogTag", news.getDate());
            newsArray.add(news);
        }

        cursor.close();

        return newsArray;
    }

    public NewsLoader loadThen() {
        load();
        return this;
    }

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

    public List<News> convertDateToView() {
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
}
