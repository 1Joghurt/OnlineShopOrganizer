package com.project.organizer.database.helper;

import android.database.sqlite.SQLiteDatabase;

import com.project.organizer.OnlineShopManagerApplication;

public class DatabaseManager {
    private static SQLiteDatabase database;

    public static SQLiteDatabase getDatabase() {
        if(database == null) {
            OpenHelper openHelper = new OpenHelper(OnlineShopManagerApplication.getContext(), null);
            database = openHelper.getWritableDatabase();
        }

        return database;
    }
}
