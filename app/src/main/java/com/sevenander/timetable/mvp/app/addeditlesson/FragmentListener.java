package com.sevenander.timetable.mvp.app.addeditlesson;

public interface FragmentListener {

    void showProgress();

    void showLoadFailed();

    void showLoadSuccess();

    void hideTitle();

    void showTitle(String title);
}
