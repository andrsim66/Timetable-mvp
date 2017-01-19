package com.sevenander.timetable.mvp.app.lessondetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sevenander.timetable.mvp.app.Injection;
import com.sevenander.timetable.mvp.app.R;
import com.sevenander.timetable.mvp.app.utils.ActivityUtils;
import com.sevenander.timetable.mvp.app.utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LessonDetailActivity extends AppCompatActivity implements FragmentListener {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.tv_title) TextView tvTitle;
    @Bind(R.id.et_title) EditText etTitle;
    @Bind(R.id.fab_edit_lesson) FloatingActionButton fabEdit;

    @Bind(R.id.sv_lesson_container) ScrollView svContent;
    @Bind(R.id.fl_progress) FrameLayout flProgress;
    @Bind(R.id.fl_no_data) FrameLayout flNoData;

    private String lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);
        ButterKnife.bind(this);

        getExtras();
        setupToolbar();
        showLessonContent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showProgress() {
        svContent.setVisibility(View.GONE);
        flNoData.setVisibility(View.GONE);
        flProgress.setVisibility(View.VISIBLE);

        etTitle.setVisibility(View.GONE);
        fabEdit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailed() {
        svContent.setVisibility(View.GONE);
        flProgress.setVisibility(View.GONE);
        flNoData.setVisibility(View.VISIBLE);

        etTitle.setVisibility(View.GONE);
        fabEdit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
    }

    @Override
    public void showLoadSuccess() {
        flProgress.setVisibility(View.GONE);
        flNoData.setVisibility(View.GONE);

        etTitle.setVisibility(View.GONE);
        fabEdit.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        svContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTitle() {
        tvTitle.setVisibility(View.GONE);
        etTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    private void getExtras() {
        lessonId = getIntent().getStringExtra(Const.KEY_LESSON_ID);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void showLessonContent() {
        LessonDetailFragment lessonDetailFragment = (LessonDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.fl_lesson_content);

        if (lessonDetailFragment == null) {
            lessonDetailFragment = LessonDetailFragment.newInstance(lessonId);

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), lessonDetailFragment, R.id.fl_lesson_content);
        }

        initPresenter(lessonDetailFragment);
    }

    private void initPresenter(LessonDetailContract.View lessonDetailView) {
        lessonId = "12345";
        new LessonDetailPresenter(
                lessonId,
                Injection.provideLessonsRepository(getApplicationContext()),
                lessonDetailView);
    }
}
