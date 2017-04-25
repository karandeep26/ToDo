package com.proj.todo.domain;

import java.util.Calendar;

/**
 * Model Class for tasks.
 */

public class TaskModel {
    private String task;
    private String date_created;
    private long id;
    private boolean isDone;

    TaskModel(String task, String date_created, long id,boolean isDone) {
        this.task = task;
        this.date_created = date_created;
        this.id = id;
        this.isDone=isDone;

    }

    public TaskModel(String task) {
        this.task = task;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int sec=calendar.get(Calendar.SECOND);
        this.date_created = (Integer.toString(date) + "-" +
                Integer.toString(month + 1) + "-" + Integer
                .toString(year)
                + " " + Integer.toString(hour) + ":" + Integer.toString(minute)+":"
                +Integer.toString(sec));
    }

    public String getDate_created() {
        return date_created;
    }


    public String getTask() {
        return task;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
    public void toggleDone(){
        isDone=!isDone;
    }
}
