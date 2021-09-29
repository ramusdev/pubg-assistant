package com.rb.pubgassistant;

import com.rb.pubgassistant.data.DataDbHelper;

public class DataConnection {
    private static DataConnection instance;
    private DataDbHelper dataDbHelper;

    private DataConnection() {
        dataDbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
    }

    public static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        }

        return instance;
    }

    public DataDbHelper getDataDbHelper() {
        if (dataDbHelper == null) {
            dataDbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        }

        return dataDbHelper;
    }
}
