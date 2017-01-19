package com.sevenander.timetable.mvp.app.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "timetable.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";

    private static final String NOT_NULL = " NOT NULL";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    LessonsContract.LessonEntry.TABLE_NAME + " (" +
                    LessonsContract.LessonEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    LessonsContract.LessonEntry.COLUMN_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_TEACHER + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_ROOM + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_START_TIME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_END_TIME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_DAY_INDEX + INT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_TYPE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LessonsContract.LessonEntry.COLUMN_COLOR + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    " UNIQUE (" + LessonsContract.LessonEntry._ID + ")" +
                    " ON CONFLICT REPLACE)" +
                    ";";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
