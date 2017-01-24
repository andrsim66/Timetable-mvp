package com.sevenander.timetable.mvp.app.addeditlesson;

import com.sevenander.timetable.mvp.app.BasePresenter;
import com.sevenander.timetable.mvp.app.BaseView;
import com.sevenander.timetable.mvp.app.data.Lesson;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AddEditLessonContract {

    interface View extends BaseView<Presenter> {

        void showEmptyLessonError();

        void showLessonsList();

        void showTitle(String title);

        void showLesson(Lesson lesson);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveLesson(Lesson lesson);

        void populateLesson();

        boolean isDataMissing();
    }
}
