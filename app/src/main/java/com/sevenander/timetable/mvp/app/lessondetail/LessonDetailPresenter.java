package com.sevenander.timetable.mvp.app.lessondetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sevenander.timetable.mvp.app.data.Lesson;
import com.sevenander.timetable.mvp.app.data.source.LessonsDataSource;
import com.sevenander.timetable.mvp.app.data.source.LessonsRepository;

import junit.framework.Assert;

/**
 * Listens to user actions from the UI ({@link LessonDetailFragment}), retrieves the data and updates
 * the UI as required.
 */
public class LessonDetailPresenter implements LessonDetailContract.Presenter {

    private final LessonsRepository lessonsRepository;
    private final LessonDetailContract.View lessonDetailView;

    @Nullable
    private String lessonId;

    public LessonDetailPresenter(@Nullable String lessonId,
                                 @NonNull LessonsRepository lessonsRepository,
                                 @NonNull LessonDetailContract.View lessonDetailView) {
        Assert.assertNotNull("LessonsRepository cannot be null!", lessonsRepository);
        Assert.assertNotNull("LessonDetailView cannot be null!", lessonDetailView);

        this.lessonId = lessonId;
        this.lessonsRepository = lessonsRepository;
        this.lessonDetailView = lessonDetailView;

        this.lessonDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if (TextUtils.isEmpty(lessonId)) {
            lessonDetailView.showMissingLesson();
            return;
        }

        lessonDetailView.setLoadingIndicator(true);
        lessonsRepository.getLessonById(lessonId, new LessonsDataSource.GetLessonCallback() {
            @Override
            public void onLessonLoaded(Lesson lesson) {
                // The view may not be able to handle UI updates anymore
                if (!lessonDetailView.isActive()) {
                    return;
                }

                lessonDetailView.setLoadingIndicator(false);
                if (null == lesson) {
                    lessonDetailView.showMissingLesson();
                } else {
                    showLesson(lesson);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!lessonDetailView.isActive()) {
                    return;
                }

                lessonDetailView.showMissingLesson();
            }
        });
    }

    @Override
    public void editTask() {
        if (TextUtils.isEmpty(lessonId)) {
            lessonDetailView.showMissingLesson();
            return;
        }
        lessonDetailView.showEditTask(lessonId);
    }

    @Override
    public void deleteTask() {
        if (TextUtils.isEmpty(lessonId)) {
            lessonDetailView.showMissingLesson();
            return;
        }
        lessonsRepository.deleteLesson(lessonId);
        lessonDetailView.showTaskDeleted();
    }

    private void showLesson(@NonNull Lesson lesson) {
        String title = lesson.getTitle();

        if (TextUtils.isEmpty(title)) {
            lessonDetailView.hideTitle();
        } else {
            lessonDetailView.showTitle(title);
        }

        lessonDetailView.showLesson(lesson);
    }
}
