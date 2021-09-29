package com.rb.pubgassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.rb.pubgassistant.data.DataContract;
import com.rb.pubgassistant.data.DataDbHelper;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TweetsUpdateCallable implements Callable<Integer> {

    private static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAMSSOAEAAAAAUrlw%2F5NK7PaofUsqVwIxqAzDwt4%3DXjFrA4cvtSMuNeS9yD4RuPHkTO1WXnWwQdJJzzvs1keEXPeNq9";
    private static final String ACCOUNT_ID = "719324795167309827";
    // private static final String ACCOUNT_ID = "14922225";

    Context context;

    public TweetsUpdateCallable() {
        this.context = MyApplicationContext.getAppContext();
    }

    @Override
    public Integer call() {

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        if (isNetworkAvailable()) {
            tweets = parse();
            insertToDatabase(tweets);
        }

        return 1;
    }

    public ArrayList<Tweet> parse() {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        TweetsParser tweetsParser = new TweetsParser(BEARER_TOKEN);
        tweets = tweetsParser.getTweets(ACCOUNT_ID);

        return tweets;
    }

    public void insertToDatabase(ArrayList<Tweet> tweets) {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        for (Tweet tweet : tweets) {
            if (! isTweetExists(tweet, sqLiteDatabase)) {
                long newRowId = sqLiteDatabase.insert(DataContract.TweetsEntry.TABLE_NAME, null, TweetValuesAdapter.convert(tweet));
            }
        }
    }

    public boolean isTweetExists(Tweet tweet, SQLiteDatabase sqLiteDatabase) {
        String selection = DataContract.TweetsEntry.COLUMN_TWEETID + " = ?";
        String[] selectionArgs = { tweet.getId() };

        Cursor cursor = sqLiteDatabase.query(DataContract.TweetsEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            // Log.e("CustomLogTag", "Entry exists in database");
            cursor.close();
            return true;
        } else {
            // Log.e("CustomLogTag", "Entry do not exists in database");
            cursor.close();
            return false;
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            );
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

}
