package com.sevenander.timetable.mvp.app.data.source.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the lessons locally.
 */
public final class LessonsContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private LessonsContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class LessonEntry implements BaseColumns {
        public static final String TABLE_NAME = "lessons";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TEACHER = "teacher";
        public static final String COLUMN_DAY_INDEX = "dayIndex";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ROOM = "room";
        public static final String COLUMN_COLOR = "color";
    }
}
