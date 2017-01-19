package com.sevenander.timetable.mvp.app.lessondetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sevenander.timetable.mvp.app.R;
import com.sevenander.timetable.mvp.app.data.Lesson;
import com.sevenander.timetable.mvp.app.utils.Const;

import junit.framework.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main UI for the task detail screen.
 */
public class LessonDetailFragment extends Fragment implements LessonDetailContract.View {

    private static final int REQUEST_EDIT_TASK = 1;

    @Bind(R.id.tv_duration_start_value) TextView tvStartTime;
    @Bind(R.id.tv_duration_end_value) TextView tvEndTime;
    @Bind(R.id.tv_teacher) TextView tvTeacher;
    @Bind(R.id.tv_day) TextView tvDay;
    @Bind(R.id.tv_room) TextView tvRoom;
    @Bind(R.id.tv_type) TextView tvType;

    private LessonDetailContract.Presenter presenter;
    private FragmentListener listener;

    public static LessonDetailFragment newInstance(@Nullable String lessonId) {
        Bundle arguments = new Bundle();
        arguments.putString(Const.KEY_LESSON_ID, lessonId);
        LessonDetailFragment fragment = new LessonDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.listener = (FragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_lesson_detail, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);

        // Set up floating action button
//        FloatingActionButton fab =
//                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.editTask();
//            }
//        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void setPresenter(@NonNull LessonDetailContract.Presenter presenter) {
        Assert.assertNotNull(presenter);
        this.presenter = presenter;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_delete:
//                presenter.deleteTask();
//                return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.taskdetail_fragment_menu, menu);
//    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            if (listener != null) listener.showProgress();
        }
    }

    @Override
    public void hideTitle() {
        if (listener != null) listener.hideTitle();
    }

    @Override
    public void showEditTask(@NonNull String taskId) {
//        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
//        intent.putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
//        startActivityForResult(intent, REQUEST_EDIT_TASK);
    }

    @Override
    public void showTaskDeleted() {
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_TASK) {
            // If the task was edited successfully, go back to the list.
            if (resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }
    }

    @Override
    public void showTitle(@NonNull String title) {
        if (listener != null) listener.showTitle(title);
    }

    @Override
    public void showLesson(Lesson lesson) {
        if (listener != null) listener.showLoadSuccess();

        //// TODO: 17.01.17 populate views
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        tvStartTime.setText(dateFormat.format(lesson.getStartTime()));
        tvEndTime.setText(dateFormat.format(lesson.getEndTime()));
        tvTeacher.setVisibility(View.VISIBLE);
        tvTeacher.setText(lesson.getTeacher());
        tvRoom.setVisibility(View.VISIBLE);
        tvRoom.setText(lesson.getRoom());

        String type = lesson.getType().name();
        type = type.charAt(0) + type.substring(1).toLowerCase();
        tvType.setText(type);
    }

    @Override
    public void showMissingLesson() {
        if (listener != null) listener.showLoadFailed();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
