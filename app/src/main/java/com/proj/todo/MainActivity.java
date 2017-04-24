package com.proj.todo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.proj.todo.adapter.TaskAdapter;
import com.proj.todo.domain.TaskDbHelper;
import com.proj.todo.domain.TaskModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    TaskAdapter adapter;
    ArrayList<TaskModel> taskModels;
    TaskDbHelper taskDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskDbHelper = new TaskDbHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.taskList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskModels = new ArrayList<>();
        adapter = new TaskAdapter(taskModels, onClickListener());
        mRecyclerView.setAdapter(adapter);
        taskModels.addAll(taskDbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText taskEditText = new EditText(this);
        taskDialog().setView(taskEditText).setPositiveButton("Add", (dialog1, which) -> {
            String task = String.valueOf(taskEditText.getText());
            TaskDbHelper taskDbHelper1 = new TaskDbHelper(MainActivity.this);
            TaskModel taskModel = new TaskModel(task);
            long id = taskDbHelper1.addTask(taskModel);
            taskModel.setId(id);
            Log.d("Tasks count", "" + taskModel.getId());
            taskModels.add(taskModel);
        }).create().show();
        return super.onOptionsItemSelected(item);
    }

    private TaskAdapter.MyAdapterListener onClickListener() {
        return (v, position) -> {
            TaskModel model = adapter.getItemAt(position);
            switch (v.getId()) {
                case R.id.done:
                    break;
                case R.id.delete:
                    if (model != null) {
                        if (taskDbHelper.deleteTask(model.getId())) {
                            adapter.removeItemAt(position);
                        }
                    }
                    break;
                case R.id.root_layout:
                    modifyTask(model, position);

            }
        };
    }

    void modifyTask(TaskModel task, int position) {
        EditText taskEditText = new EditText(this);
        taskEditText.setText(task.getTask());
        taskDialog().setView(taskEditText).setPositiveButton("Add", (dialog1, which) -> {
            String newTask = String.valueOf(taskEditText.getText());
            if (!newTask.trim().equals(task.getTask().trim())) {
                task.setTask(newTask);
                taskDbHelper.upDate(task);
                adapter.updateTask(task, position);
            }
        }).create().show();

    }

    private AlertDialog.Builder taskDialog() {
        return new AlertDialog.Builder(this).setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setNegativeButton("Cancel", null);
    }
}
