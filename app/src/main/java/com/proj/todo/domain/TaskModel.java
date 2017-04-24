package com.proj.todo.domain;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stpl on 4/24/2017.
 */

public class TaskModel implements Comparable<TaskModel> {
    private String task;
    private String date_created;
    private long id;

    TaskModel(String task, String date_created, long id) {
        this.task = task;
        this.date_created = date_created;
        this.id = id;

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


    @Override
    public int compareTo(@NonNull TaskModel o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        int result = 0;
        try {
            Date date1 = simpleDateFormat.parse(date_created);
            Date date2 = simpleDateFormat.parse(o.getDate_created());
            result = date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return result;
    }
}
