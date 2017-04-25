package com.proj.todo.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.proj.todo.R;
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView
                .getContext(),
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                taskDialog().setView(taskEditText).setPositiveButton("Add", (dialog1, which) -> {
                    String taskContent = String.valueOf(taskEditText.getText());
                    TaskModel taskModel = new TaskModel(taskContent);
                    long id = taskDbHelper.addTask(taskModel);
                    taskModel.setId(id);
                    Log.d("Tasks count", "" + taskModel.getId());
                    taskModels.add(taskModel);
                }).create().show();
                break;
            case R.id.action_sort_task:
                View dialogView = View.inflate(this, R.layout.sort_dialog, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(dialogView)
                        .create();
                RelativeLayout asc, dsc;
                asc = (RelativeLayout) dialogView.findViewById(R.id.asc);
                dsc = (RelativeLayout) dialogView.findViewById(R.id.dsc);
                asc.setOnClickListener(v -> {
                            adapter.sortAscData();
                            alertDialog.dismiss();
                        }
                );
                dsc.setOnClickListener(v -> {
                    adapter.sortDscData();
                    alertDialog.dismiss();
                });

                alertDialog.show();

        }
        return super.onOptionsItemSelected(item);

    }

    private TaskAdapter.MyAdapterListener onClickListener() {
        return (v, position) -> {
            TaskModel model = adapter.getItemAt(position);
            switch (v.getId()) {
                case R.id.done:
                    model.toggleDone();
                    taskDbHelper.upDate(model);
                    adapter.updateTask(model, position);
                    break;
                case R.id.delete:
                    if (model != null) {
                        if (taskDbHelper.deleteTask(model.getId())) {
                            adapter.removeItemAt(position);
                        } else {
                            Toast.makeText(MainActivity.this, "Some Error Occurred", Toast
                                    .LENGTH_SHORT).show();
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
        taskEditText.setSelection(task.getTask().length());
        taskDialog().setView(taskEditText).setPositiveButton("Add", (dialog1, which) -> {
            String newTaskContent = String.valueOf(taskEditText.getText());
            if (!newTaskContent.trim().equals(task.getTask().trim())) {
                task.setTask(newTaskContent);
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
