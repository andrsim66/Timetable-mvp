package com.sevenander.timetable.mvp.app.lessondetail;

public interface FragmentListener {

    void showProgress();

    void showLoadFailed();

    void showLoadSuccess();

    void hideTitle();

    void showTitle(String title);
}
