package com.example.crudapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskmanager.db";
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " +
                TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskContract.TaskEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_DESCRIPTION + " TEXT, " +
                TaskContract.TaskEntry.COLUMN_DUE_DATE + " TEXT" +
                ");";
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for task data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + com.example.crudapp.DB.TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
