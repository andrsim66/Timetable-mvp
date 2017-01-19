package com.sevenander.timetable.mvp.app.utils;

public class Const {

    public static final String KEY_LESSON_ID = "lessonId";

    /**
     * Prevention from constructing objects of this class.
     */
    private Const() {
        //this prevents even the native class from
        //calling this constructor as well:
        throw new AssertionError();
    }
}
