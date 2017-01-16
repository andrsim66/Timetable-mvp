package com.sevenander.timetable.mvp.app.data.source;

import android.support.annotation.NonNull;

import com.sevenander.timetable.mvp.app.data.Lesson;

import java.util.List;

public interface LessonsDataSource {

    interface LoadLessonsCallback {

        void onLessonsLoaded(List<Lesson> lessons);

        void onDataNotAvailable();
    }

    interface GetLessonCallback {

        void onLessonLoaded(Lesson lesson);

        void onDataNotAvailable();
    }

    void getLessonsByDay(@NonNull int dayIndex, @NonNull LoadLessonsCallback callback);

    void getLessonById(@NonNull String lessonId, @NonNull GetLessonCallback callback);

    void addLesson(@NonNull Lesson lesson);

    void updateLesson(@NonNull Lesson lesson);

    void deleteLesson(@NonNull String lessonId);

    void deleteLessons();
}
