package com.sevenander.timetable.mvp.app.data.source.local;


import android.content.ContentValues;
import android.database.Cursor;

import com.sevenander.timetable.mvp.app.data.Lesson;

public class CursorMapper {

    static Lesson getLessonFromCursor(Cursor cursor) {
        String lessonId = cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry._ID));
        String title = cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_TITLE));
        String teacher = cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_TEACHER));
        String room = cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_ROOM));

        long startTime = Long.valueOf(cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_START_TIME)));
        long endTime = Long.valueOf(cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_END_TIME)));

        int dayIndex = cursor.getInt(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_DAY_INDEX));

        Lesson.LessonType type = Lesson.LessonType.valueOf(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_TYPE)));

        String color = cursor.getString(
                cursor.getColumnIndexOrThrow(LessonsContract.LessonEntry.COLUMN_COLOR));

        Lesson lesson = new Lesson(lessonId);
        lesson.setTitle(title);
        lesson.setTeacher(teacher);
        lesson.setRoom(room);
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);
        lesson.setDayIndex(dayIndex);
        lesson.setType(type);
        lesson.setColor(color);

        return lesson;
    }

    static ContentValues putLesson(Lesson lesson) {
        ContentValues values = new ContentValues();
        values.put(LessonsContract.LessonEntry.COLUMN_TITLE, lesson.getTitle());
        values.put(LessonsContract.LessonEntry.COLUMN_TEACHER, lesson.getTeacher());
        values.put(LessonsContract.LessonEntry.COLUMN_ROOM, lesson.getRoom());
        values.put(LessonsContract.LessonEntry.COLUMN_START_TIME, lesson.getStartTime());
        values.put(LessonsContract.LessonEntry.COLUMN_END_TIME, lesson.getEndTime());
        values.put(LessonsContract.LessonEntry.COLUMN_DAY_INDEX, lesson.getDayIndex());
        values.put(LessonsContract.LessonEntry.COLUMN_TYPE, lesson.getType().name());
        values.put(LessonsContract.LessonEntry.COLUMN_COLOR, lesson.getColor());
        return values;
    }
}
