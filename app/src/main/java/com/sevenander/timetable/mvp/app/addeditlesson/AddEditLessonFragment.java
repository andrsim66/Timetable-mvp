package com.sevenander.timetable.mvp.app.addeditlesson;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sevenander.timetable.mvp.app.R;
import com.sevenander.timetable.mvp.app.data.Lesson;
import com.sevenander.timetable.mvp.app.lessondetail.FragmentListener;

import junit.framework.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class AddEditLessonFragment extends Fragment implements AddEditLessonContract.View {

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    @Bind(R.id.tv_duration_start_value) TextView tvStartTime;
    @Bind(R.id.tv_duration_end_value) TextView tvEndTime;
    @Bind(R.id.et_teacher) EditText etTeacher;
    @Bind(R.id.tv_day) TextView tvDay;
    @Bind(R.id.et_room) EditText etRoom;
    @Bind(R.id.tv_type) TextView tvType;

    private AddEditLessonContract.Presenter presenter;
    private FragmentListener listener;

    public static AddEditLessonFragment newInstance(String lessonId) {
        AddEditLessonFragment fragment = new AddEditLessonFragment();

        if (!TextUtils.isEmpty(lessonId)){
            Bundle args = new Bundle();

            fragment.setArguments(args);
        }

        return fragment;
    }

    public AddEditLessonFragment() {
        // Required empty public constructor
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
//        mTitle = (TextView) root.findViewById(R.id.add_task_title);
//        mDescription = (TextView) root.findViewById(R.id.add_task_description);
        setHasOptionsMenu(true);

        etTeacher.setVisibility(View.VISIBLE);
        etRoom.setVisibility(View.VISIBLE);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        FloatingActionButton fab =
//                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
//        fab.setImageResource(R.drawable.ic_done);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.saveTask(mTitle.getText().toString(), mDescription.getText().toString());
//            }
//        });
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
    public void setPresenter(@NonNull AddEditLessonContract.Presenter presenter) {
        Assert.assertNotNull(presenter);
        this.presenter = presenter;
    }

    @Override
    public void showEmptyLessonError() {
//        Snackbar.make(mTitle, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLessonsList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showTitle(String title) {
        if (listener != null) listener.showTitle(title);
    }

    @Override
    public void showLesson(Lesson lesson) {
        if (listener != null) listener.showLoadSuccess();

        //// TODO: 17.01.17 populate views
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        tvStartTime.setText(dateFormat.format(lesson.getStartTime()));
        tvEndTime.setText(dateFormat.format(lesson.getEndTime()));
        etTeacher.setText(lesson.getTeacher());
        etRoom.setText(lesson.getRoom());

        String type = lesson.getType().name();
        type = type.charAt(0) + type.substring(1).toLowerCase();
        tvType.setText(type);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
