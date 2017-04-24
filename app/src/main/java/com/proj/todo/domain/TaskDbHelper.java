package com.proj.todo.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by stpl on 4/24/2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactsManager.db";
    private static final String TABLE_TASKS="tasks";
    private static int VERSION=1;
    private static final String KEY_ID="id";
    private static final String KEY_TASK_CONTENT="content";
    private static final String KEY_DATE="date_created";
    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TASK_CONTENT + " TEXT,"
                +KEY_DATE+" TEXT)";
        db.execSQL(CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // Create tables again
        onCreate(db);
    }
    public long addTask(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();
        long id;
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_CONTENT,task.getTask());
        values.put(KEY_DATE,task.getDate_created());
        id=db.insert(TABLE_TASKS,null,values);
        db.close();
        return id;
    }
    public int getTasksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public ArrayList<TaskModel> getAllTasks(){
        ArrayList<TaskModel> taskModels=new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                TaskModel model=new TaskModel(cursor.getString(1),cursor.getString(2),cursor.getInt(0));
                taskModels.add(model);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return taskModels;

    }
    public boolean deleteTask(long id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_TASKS,KEY_ID+" = ?",new String[]{id+""})>0;

    }


    public void upDate(TaskModel task) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_CONTENT,task.getTask());
        values.put(KEY_DATE,task.getDate_created());
        db.update(TABLE_TASKS,values,KEY_ID+" = ?",new String[]{task.getId()+""});
    }
}
