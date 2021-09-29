package com.rb.pubgassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rb.pubgassistant.data.DataDbHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseEntity<T> {

    public T[] data;
    public Class<T> classTyped;

    public DatabaseEntity(Class<T> classTyped) {
        this.classTyped = classTyped;
    }

    public void insert(T[] data) {
        this.data = data;

        Log.d("MyTag", "Insert");
        String className = classTyped.getSimpleName();
        String tableName = className.toLowerCase() + "s";
        Field[] fields = classTyped.getDeclaredFields();

        DataConnection dataConnection = DataConnection.getInstance();
        DataDbHelper dataDbHelper = dataConnection.getDataDbHelper();
        SQLiteDatabase sqLiteDatabase = dataDbHelper.getWritableDatabase();

        for (int i = 0; i < data.length; ++i) {
            ContentValues contentValues = adapter(data[i], fields);
            sqLiteDatabase.insert(tableName, null, contentValues);
        }
    }

    public void clear() {
        String className = classTyped.getSimpleName();
        String tableName = className.toLowerCase() + "s";

        DataConnection dataConnection = DataConnection.getInstance();
        DataDbHelper dataDbHelper = dataConnection.getDataDbHelper();
        SQLiteDatabase sqLiteDatabase = dataDbHelper.getWritableDatabase();

        String deleteQuery = "DELETE" +
                " FROM " + tableName;

        sqLiteDatabase.execSQL(deleteQuery);
    }

    public List<T> load() {

        String className = classTyped.getSimpleName();
        String tableName = className.toLowerCase() + "s";

        DataConnection dataConnection = DataConnection.getInstance();
        DataDbHelper dataDbHelper = dataConnection.getDataDbHelper();
        SQLiteDatabase sqLiteDatabase = dataDbHelper.getReadableDatabase();

        String limit = "30";
        Cursor cursor = sqLiteDatabase.query(tableName,
                null,
                null,
                null,
                null,
                null,
                null,
                limit
        );

        List<T> someObjects = new ArrayList<T>();

        while (cursor.moveToNext()) {
            try {
                T someObject = classTyped.getDeclaredConstructor().newInstance();
                filler(someObject, cursor);
                someObjects.add(someObject);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Log.d("MyTag", e.getMessage());
            }
        }

        cursor.close();

        return someObjects;
    }

    public void filler(T someObject, Cursor cursor) {
        Field[] fields = classTyped.getDeclaredFields();

        for (Field field : fields) {
            try {
                String name = field.getName();
                String value = cursor.getString(cursor.getColumnIndexOrThrow(name));
                field.set(someObject, value);
            } catch(IllegalAccessException e) {
                System.out.println("Error filler: " + e.getMessage());
            }
        }
    }

    public ContentValues adapter(T someClass, Field[] fields) {
        ContentValues contentValues = new ContentValues();

        for (int k = 0; k < fields.length; ++k) {
            try {
                // Log.d("MyTag", "adapter name");
                Field field = fields[k];
                String name = field.getName();
                // Log.d("MyTag", name);
                Object value = field.get(someClass);
                // Log.d("MyTag", value.toString());

                if (value instanceof String) {
                    contentValues.put(name, (String) value);
                } else if (value instanceof Integer) {
                    contentValues.put(name, (int) value);
                }

           } catch (IllegalAccessException e) {
                Log.d("MyTag", e.getMessage());
           }


        }

        return contentValues;
    }
}
