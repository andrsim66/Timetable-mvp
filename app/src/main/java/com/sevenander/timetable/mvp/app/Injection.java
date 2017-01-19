package com.sevenander.timetable.mvp.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sevenander.timetable.mvp.app.data.source.LessonsDataSource;
import com.sevenander.timetable.mvp.app.data.source.LessonsRepository;
import com.sevenander.timetable.mvp.app.data.source.local.LessonsLocalDataSource;

import junit.framework.Assert;

/**
 * Enables injection of mock implementations for
 * {@link LessonsDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static LessonsRepository provideLessonsRepository(@NonNull Context context) {
        Assert.assertNotNull(context);
        return LessonsRepository.getInstance(LessonsLocalDataSource.getInstance(context));
    }
}
