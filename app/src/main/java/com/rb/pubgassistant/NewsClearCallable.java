package com.rb.pubgassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rb.pubgassistant.data.DataContract;
import com.rb.pubgassistant.data.DataDbHelper;

import java.util.concurrent.Callable;

public class NewsClearCallable implements Callable<Integer> {

    private Context context;

    public NewsClearCallable() {
        this.context = MyApplicationContext.getAppContext();
    }

    @Override
    public Integer call() {
        clearNews();

        return 1;
    }

    public void clearNews() {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        String deleteQuery = "DELETE" +
                " FROM " + DataContract.NewsEntry.TABLE_NAME +
                " WHERE " + DataContract.NewsEntry._ID +
                " NOT IN (SELECT " + DataContract.NewsEntry._ID +
                " FROM " + DataContract.NewsEntry.TABLE_NAME +
                " ORDER BY " + DataContract.NewsEntry._ID +
                " DESC " +
                " LIMIT 20)";

        sqLiteDatabase.execSQL(deleteQuery);
    }
}
