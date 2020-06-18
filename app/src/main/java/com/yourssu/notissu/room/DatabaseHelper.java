package com.yourssu.notissu.room;

import android.content.Context;

import androidx.room.Room;
import androidx.room.migration.Migration;

public class DatabaseHelper {
    private volatile static DatabaseHelper instance = null;

    private AppDatabase appDatabaseInstance = null;

    private DatabaseHelper() {}

    public static DatabaseHelper getDatabase(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper();
                    instance.appDatabaseInstance = Room.databaseBuilder(
                            context,
                            AppDatabase.class,
                            "bookmark.db"
                    )
                            .build();
                }
            }
        }
        return instance;
    }

    public AppDatabase appDatabase() {
        return appDatabaseInstance;
    }
}
