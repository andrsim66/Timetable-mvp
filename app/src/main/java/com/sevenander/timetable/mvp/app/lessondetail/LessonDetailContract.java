package com.sevenander.timetable.mvp.app.lessondetail;

import com.sevenander.timetable.mvp.app.BasePresenter;
import com.sevenander.timetable.mvp.app.BaseView;
import com.sevenander.timetable.mvp.app.data.Lesson;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LessonDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingLesson();

        void hideTitle();

        void showTitle(String title);

        void showLesson(Lesson lesson);

        void showEditTask(String taskId);

        void showTaskDeleted();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();
    }
}
