package com.sevenander.timetable.mvp.app.addeditlesson;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sevenander.timetable.mvp.app.data.Lesson;
import com.sevenander.timetable.mvp.app.data.source.LessonsDataSource;

import junit.framework.Assert;


/**
 * Listens to user actions from the UI ({@link AddEditLessonFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AddEditLessonPresenter implements AddEditLessonContract.Presenter,
        LessonsDataSource.GetLessonCallback {

    @NonNull
    private final LessonsDataSource lessonsDataSource;

    @NonNull
    private final AddEditLessonContract.View addLessonView;

    @Nullable private String lessonId;

    private boolean isDataMissing;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param lessonId               ID of the lesson to edit or null for a new lesson
     * @param lessonsDataSource      a repository of data for lessons
     * @param addLessonView          the add/edit view
     * @param shouldLoadDataFromRepo whether data needs to be loaded or not (for config changes)
     */
    public AddEditLessonPresenter(@Nullable String lessonId,
                                  @NonNull LessonsDataSource lessonsDataSource,
                                  @NonNull AddEditLessonContract.View addLessonView,
                                  boolean shouldLoadDataFromRepo) {
        Assert.assertNotNull(lessonsDataSource);
        Assert.assertNotNull(addLessonView);

        this.lessonId = lessonId;
        this.lessonsDataSource = lessonsDataSource;
        this.addLessonView = addLessonView;
        this.isDataMissing = shouldLoadDataFromRepo;

        this.addLessonView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isNewLesson() && isDataMissing) {
            populateLesson();
        }
    }

    @Override
    public void saveLesson(Lesson lesson) {
        if (isNewLesson()) {
            createLesson(lesson);
        } else {
            updateLesson(lesson);
        }
    }

    @Override
    public void populateLesson() {
        if (isNewLesson()) {
            throw new RuntimeException("populateLesson() was called but lesson is new.");
        }
        lessonsDataSource.getLessonById(lessonId, this);
    }

    @Override
    public void onLessonLoaded(Lesson lesson) {
        // The view may not be able to handle UI updates anymore
        if (addLessonView.isActive()) {
            addLessonView.showTitle(lesson.getTitle());
            addLessonView.showLesson(lesson);
        }
        isDataMissing = false;
    }

    @Override
    public void onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (addLessonView.isActive()) {
            addLessonView.showEmptyLessonError();
        }
    }

    @Override
    public boolean isDataMissing() {
        return isDataMissing;
    }

    private boolean isNewLesson() {
        return lessonId == null;
    }

    private void createLesson(Lesson lesson) {
        if (lesson == null) {
            addLessonView.showEmptyLessonError();
        } else {
            lessonsDataSource.addLesson(lesson);
            addLessonView.showLessonsList();
        }
    }

    private void updateLesson(Lesson lesson) {
        if (isNewLesson()) {
            throw new RuntimeException("updateLesson() was called but lesson is new.");
        }
        lessonsDataSource.updateLesson(lesson);
        addLessonView.showLessonsList(); // After an edit, go back to the list.
    }
}
