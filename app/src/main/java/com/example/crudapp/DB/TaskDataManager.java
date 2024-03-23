package com.example.crudapp.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crudapp.Task;

import java.util.ArrayList;

public class TaskDataManager {
    private TaskDbHelper dbHelper;

    public TaskDataManager(Context context) {
        dbHelper = new TaskDbHelper(context);
    }

    public void insertTask(String title, String description, String dueDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, dueDate);

        db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TITLE,
                TaskContract.TaskEntry.COLUMN_DESCRIPTION,
                TaskContract.TaskEntry.COLUMN_DUE_DATE
        };

        String sortOrder = TaskContract.TaskEntry.COLUMN_DUE_DATE + " ASC";  // Sort by due date in ascending order

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION));
            String dueDate = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DUE_DATE));
            tasks.add(new Task(id, title, description, dueDate));
        }
        cursor.close();

        return tasks;
    }



    public void deleteTask(int taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(taskId) };
        db.delete(TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
    }

    public Task getTask(int taskId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TITLE,
                TaskContract.TaskEntry.COLUMN_DESCRIPTION,
                TaskContract.TaskEntry.COLUMN_DUE_DATE
        };
        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(taskId) };

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Task task = null;
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION));
            String dueDate = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DUE_DATE));
            task = new Task(taskId, title, description, dueDate);
        }
        cursor.close();
        return task;
    }

    public void updateTask(int taskId, String title, String description, String dueDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, dueDate);

        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(taskId) };

        db.update(TaskContract.TaskEntry.TABLE_NAME, values, selection, selectionArgs);
    }



    // Add methods for updating and deleting tasks as needed...
}
