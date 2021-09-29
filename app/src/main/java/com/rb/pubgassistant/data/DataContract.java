package com.rb.pubgassistant.data;

import android.provider.BaseColumns;

public final class DataContract {

    private DataContract() {

    }

    public static final class NewsEntry implements BaseColumns {
        public final static String TABLE_NAME = "news";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PREVIEWTEXT = "previewtext";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_NOTIFIED = "notified";
    }

    public static final class TweetsEntry implements BaseColumns {
        public final static String TABLE_NAME = "tweets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TWEETID = "tweetid";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_NOTIFIED = "notified";
    }

    public static final class WallpaperEntry implements BaseColumns {
        public final static String TABLE_NAME = "wallpapers";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_IMAGE = "image";
    }

    public static final class LegendsEntry implements BaseColumns {
        public final static String TABLE_NAME = "legends";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_TAGLINE = "tagline";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
