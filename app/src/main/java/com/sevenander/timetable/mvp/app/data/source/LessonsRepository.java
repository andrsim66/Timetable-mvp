package com.sevenander.timetable.mvp.app.data.source;

import android.support.annotation.NonNull;

import com.sevenander.timetable.mvp.app.data.Lesson;

import junit.framework.Assert;

public class LessonsRepository implements LessonsDataSource {

    private static LessonsRepository instance = null;

    private final LessonsDataSource lessonsLocalDataSource;

    // Prevent direct instantiation.
    private LessonsRepository(@NonNull LessonsDataSource tasksLocalDataSource) {
        Assert.assertNotNull(tasksLocalDataSource);
        lessonsLocalDataSource = tasksLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param lessonsLocalDataSource the device storage data source
     * @return the {@link LessonsRepository} instance
     */
    public static LessonsRepository getInstance(LessonsDataSource lessonsLocalDataSource) {
        if (instance == null) {
            instance = new LessonsRepository(lessonsLocalDataSource);
        }
        return instance;
    }

    /**
     * Used to force {@link #getInstance(LessonsDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public void getLessonsByDay(@NonNull int dayIndex, @NonNull LoadLessonsCallback callback) {

    }

    @Override
    public void getLessonById(@NonNull String lessonId, @NonNull GetLessonCallback callback) {

    }

    @Override
    public void addLesson(@NonNull Lesson lesson) {

    }

    @Override
    public void updateLesson(@NonNull Lesson lesson) {

    }

    @Override
    public void deleteLesson(@NonNull String lessonId) {

    }

    @Override
    public void deleteLessons() {

    }
}
