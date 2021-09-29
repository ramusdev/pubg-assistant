package com.rb.pubgassistant;

import android.content.ContentValues;
import com.rb.pubgassistant.data.DataContract;

public class NewsValuesAdapter implements ValuesAdapterInterface {

    public static ContentValues convert(News news) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.NewsEntry.COLUMN_TITLE, news.getTitle());
        contentValues.put(DataContract.NewsEntry.COLUMN_DATE, news.getDate());
        contentValues.put(DataContract.NewsEntry.COLUMN_IMAGE, news.getImage());
        contentValues.put(DataContract.NewsEntry.COLUMN_LINK, news.getLink());
        contentValues.put(DataContract.NewsEntry.COLUMN_PREVIEWTEXT, news.getPreviewText());
        contentValues.put(DataContract.NewsEntry.COLUMN_TEXT, news.getText());
        contentValues.put(DataContract.NewsEntry.COLUMN_NOTIFIED, 0);

        return contentValues;
    }
}
