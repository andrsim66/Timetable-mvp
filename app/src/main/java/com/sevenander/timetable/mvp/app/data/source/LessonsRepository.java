package com.sevenander.timetable.mvp.app.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sevenander.timetable.mvp.app.data.Lesson;

import junit.framework.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class LessonsRepository implements LessonsDataSource {

    private static LessonsRepository instance = null;

    private final LessonsDataSource lessonsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Lesson> cachedLessons;

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
    public void getLessonById(@NonNull final String lessonId, @NonNull final GetLessonCallback callback) {
        Assert.assertNotNull(lessonId);
        Assert.assertNotNull(callback);

        Lesson cachedLesson = getLessonWithId(lessonId);
        // Respond immediately with cache if available
        if (cachedLesson != null) {
            callback.onLessonLoaded(cachedLesson);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        lessonsLocalDataSource.getLessonById(lessonId, new GetLessonCallback() {
            @Override
            public void onLessonLoaded(Lesson lesson) {
                onSuccessLessonLoad(lesson, callback);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void onSuccessLessonLoad(Lesson lesson, GetLessonCallback callback) {
        // Do in memory cache update to keep the app UI up to date
        if (cachedLessons == null) {
            cachedLessons = new LinkedHashMap<>();
        }
        cachedLessons.put(lesson.getId(), lesson);
        callback.onLessonLoaded(lesson);
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

    @Nullable
    private Lesson getLessonWithId(@NonNull String id) {
        Assert.assertNotNull(id);
        if (cachedLessons == null || cachedLessons.isEmpty()) {
            return null;
        } else {
            return cachedLessons.get(id);
        }
    }
}
