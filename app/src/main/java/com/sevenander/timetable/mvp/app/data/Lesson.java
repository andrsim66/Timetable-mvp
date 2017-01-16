package com.sevenander.timetable.mvp.app.data;

import java.util.UUID;

public class Lesson {

    private String id;
    private String title;
    private String teacher;
    private String room;

    //format - HH:MM
    private long startTime;
    private long endTime;

    private int dayIndex;
    private LessonType type;

    private String color;

    public Lesson() {
        this.id = UUID.randomUUID().toString();
    }

    public Lesson(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public enum LessonType {
        LECTURE,
        LABS,
        PRACTICE,
        CONSULTATION,
        OTHER
    }
}
