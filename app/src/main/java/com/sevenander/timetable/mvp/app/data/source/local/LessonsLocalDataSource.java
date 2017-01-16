package com.sevenander.timetable.mvp.app.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.sevenander.timetable.mvp.app.data.Lesson;
import com.sevenander.timetable.mvp.app.data.source.LessonsDataSource;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

public class LessonsLocalDataSource implements LessonsDataSource {

    private static LessonsLocalDataSource instance;

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    // Prevent direct instantiation.
    private LessonsLocalDataSource(@NonNull Context context) {
        Assert.assertNotNull(context);
        this.dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public static LessonsLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new LessonsLocalDataSource(context);
        }
        return instance;
    }

    @Override
    public void getLessonsByDay(int dayIndex, @NonNull LoadLessonsCallback callback) {
        List<Lesson> lessons = new ArrayList<>();

        String selection = LessonsContract.LessonEntry.COLUMN_DAY_INDEX + " = ?";
        String[] selectionArgs = {"" + dayIndex};

        Cursor c = db.query(LessonsContract.LessonEntry.TABLE_NAME, null,
                selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Lesson lesson = CursorMapper.getLessonFromCursor(c);
                lessons.add(lesson);
            }
        }

        if (c != null) {
            c.close();
        }

        db.close();

        if (lessons.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onLessonsLoaded(lessons);
        }
    }

    @Override
    public void getLessonById(@NonNull String lessonId, @NonNull GetLessonCallback callback) {
        String selection = LessonsContract.LessonEntry._ID + " = ?";
        String[] selectionArgs = {lessonId};

        Cursor cursor = db.query(LessonsContract.LessonEntry.TABLE_NAME, null,
                selection, selectionArgs, null, null, null);

        Lesson lesson = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            lesson = CursorMapper.getLessonFromCursor(cursor);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (lesson != null) {
            callback.onLessonLoaded(lesson);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addLesson(@NonNull Lesson lesson) {
        Assert.assertNotNull(lesson);

        ContentValues values = CursorMapper.putLesson(lesson);
        values.put(LessonsContract.LessonEntry._ID, lesson.getId());

        db.insert(LessonsContract.LessonEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updateLesson(@NonNull Lesson lesson) {
        Assert.assertNotNull(lesson);

        ContentValues values = CursorMapper.putLesson(lesson);

        String selection = LessonsContract.LessonEntry._ID + " = ?";
        String[] selectionArgs = {lesson.getId()};

        db.update(LessonsContract.LessonEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void deleteLesson(@NonNull String lessonId) {
        String selection = LessonsContract.LessonEntry._ID + " = ?";
        String[] selectionArgs = {lessonId};

        db.delete(LessonsContract.LessonEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    @Override
    public void deleteLessons() {

    }
}
